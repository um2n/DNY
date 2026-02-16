package com.dny.dny.service;

import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.entity.Job;
import com.dny.dny.repository.JobRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final BookmarkService bookmarkService;

    @Value("${job.api.key}")
    private String serviceKey;

    private static final String BASE_URL = "https://apis.data.go.kr/1051000/recruitment/list";

    /* 통합 공고 조회 (필터링 + 페이징 + 마감기한 체크) */
    public Page<JobResponseDto> getJobs(String keyword, String location, String jobType, Long userId, Pageable pageable) {
        
        Specification<Job> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 1. 기본 조건: 마감 기한이 오늘 이후거나 없음
            predicates.add(cb.or(
                cb.greaterThanOrEqualTo(root.get("deadline"), LocalDate.now()),
                cb.isNull(root.get("deadline"))
            ));

            // 2. 검색어 필터 (제목)
            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(root.get("title"), "%" + keyword + "%"));
            }

            // 3. 지역 필터
            if (location != null && !location.isBlank()) {
                predicates.add(cb.equal(root.get("location"), location));
            }

            // 4. 채용 구분 필터
            if (jobType != null && !jobType.isBlank()) {
                predicates.add(cb.equal(root.get("jobType"), jobType));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Job> jobPage = jobRepository.findAll(spec, pageable);
        return convertToResponseDtoPage(jobPage, userId, pageable);
    }

    private Page<JobResponseDto> convertToResponseDtoPage(Page<Job> jobPage, Long userId, Pageable pageable) {
        Set<String> bookmarkedIds = bookmarkService.getBookmarkedJobIds(userId);
        List<JobResponseDto> dtoList = jobPage.getContent().stream()
                .map(job -> new JobResponseDto(
                        job.getJobId(), job.getTitle(), job.getCompany(),
                        job.getLocation(), job.getJobType(), job.getDeadline(),
                        job.getCreatedAt(), bookmarkedIds.contains(job.getJobId())
                )).toList();
        return new PageImpl<>(dtoList, pageable, jobPage.getTotalElements());
    }

    @Async
    @Transactional
    public void saveJobsToDb() {
        System.out.println("🚀 [Background] 공고 업데이트 시작...");
        jobRepository.deleteByDeadlineBefore(LocalDate.now());
        
        List<JobDto> apiJobs = getItJobs();
        if (apiJobs.isEmpty()) return;

        Set<String> existingIds = new HashSet<>(jobRepository.findAllJobIds());
        List<Job> newJobs = apiJobs.stream()
                .filter(dto -> !existingIds.contains(dto.getRecrutPblntSn()))
                .map(this::convertToEntity)
                .toList();

        if (!newJobs.isEmpty()) jobRepository.saveAll(newJobs);
        System.out.println("✅ 신규 저장 완료: " + newJobs.size() + "건");
    }

    public List<JobDto> getItJobs() {
        RestTemplate restTemplate = new RestTemplate();
        String firstUrl = buildUrl(1, 100);
        JobApiResponse firstResponse = restTemplate.getForObject(firstUrl, JobApiResponse.class);
        
        if (firstResponse == null || firstResponse.getResult() == null) return Collections.emptyList();

        int totalPages = Math.min((int) Math.ceil((double) firstResponse.getTotalCount() / 100), 50);
        
        return java.util.stream.IntStream.rangeClosed(1, totalPages)
                .parallel()
                .mapToObj(page -> restTemplate.getForObject(buildUrl(page, 100), JobApiResponse.class))
                .filter(res -> res != null && res.getResult() != null)
                .flatMap(res -> res.getResult().stream())
                .filter(this::isItNewbieJob)
                .filter(job -> {
                    LocalDate deadline = parseDate(job.getPbancEndYmd());
                    return deadline == null || !deadline.isBefore(LocalDate.now());
                }).toList();
    }

    private String buildUrl(int page, int size) {
        return UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", page)
                .queryParam("numOfRows", size)
                .queryParam("resultType", "json")
                .build().toUriString();
    }

    private boolean isItNewbieJob(JobDto job) {
        String title = job.getRecrutPbancTtl();
        String type = job.getRecrutSeNm();
        if (title == null || type == null || !type.contains("신입")) return false;
        
        String lower = title.toLowerCase();
        List<String> keywords = List.of("전산", "정보보안", "정보보호", "네트워크", "시스템", "서버", "it ", "ict", "정보기술", "사이버보안");
        List<String> exclude = List.of("연구", "관리", "ami", "정비", "운전", "간호", "환경", "미화", "배전", "전력", "기계");
        
        return keywords.stream().anyMatch(lower::contains) && exclude.stream().noneMatch(lower::contains);
    }

    private Job convertToEntity(JobDto dto) {
        Job job = new Job();
        job.setJobId(dto.getRecrutPblntSn());
        job.setTitle(dto.getRecrutPbancTtl());
        job.setCompany(dto.getInstNm());
        job.setLocation(dto.getWorkRgnNmLst());
        job.setJobType(dto.getRecrutSeNm());
        job.setDeadline(parseDate(dto.getPbancEndYmd()));
        job.setCreatedAt(parseDate(dto.getPbancBgngYmd()));
        return job;
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return (dateStr == null || dateStr.isBlank()) ? null : LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) { return null; }
    }
}
