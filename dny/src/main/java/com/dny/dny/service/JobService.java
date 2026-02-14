package com.dny.dny.service;

import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.entity.Bookmark;
import com.dny.dny.entity.Job;
import com.dny.dny.repository.BookmarkRepository;
import com.dny.dny.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final BookmarkRepository bookmarkRepository;

    private static final String BASE_URL =
            "https://apis.data.go.kr/1051000/recruitment/list";

    @Value("${job.api.key}")
    private String serviceKey;

    private static final String[] IT_KEYWORDS = {
            "정보", "전산", "it", "소프트웨어", "시스템",
            "데이터", "정보보안", "정보보호", "정보화",
            "개발자", "db", "웹", "서버", "네트워크",
            "sw", "프로그래밍", "정보통신", "ict"
    };

    /**
     * 🔹 DB 기준 공고 조회 + 북마크 여부 포함
     */
    public List<JobResponseDto> getJobsWithBookmark(Long userId) {

        List<Job> jobs = jobRepository.findAll();

        Set<String> bookmarkedIds = bookmarkRepository.findByUserId(userId)
                .stream()
                .map(Bookmark::getJobId)
                .collect(Collectors.toSet());

        return jobs.stream()
                .map(job -> new JobResponseDto(
                        job.getJobId(),
                        job.getTitle(),
                        job.getCompany(),
                        job.getLocation(),
                        job.getJobType(),
                        job.getDeadline(),
                        job.getCreatedAt(),
                        bookmarkedIds.contains(job.getJobId())
                ))
                .collect(Collectors.toList());
    }

    /**
     * 🔹 외부 API 호출
     */
    public List<JobDto> getItJobs() {

        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 5)
                .queryParam("resultType", "json")
                .build()
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        JobApiResponse response =
                restTemplate.getForObject(url, JobApiResponse.class);

        if (response == null || response.getResult() == null) {
            return List.of();
        }

        return response.getResult().stream()
                .filter(this::isEntry)               // 신입 포함
                .filter(this::isItByTitleOrNcs)      // IT 키워드
                .filter(this::isNotExpired)          // 마감 안 지난 것
                .toList();
    }




    /**
     * 🔥 API → DB 저장
     */
    @Transactional
    public void saveJobsToDb() {

        List<JobDto> jobs = getItJobs();

        for (JobDto dto : jobs) {

            String jobId = dto.getRecrutPblntSn();

            System.out.println("ID 값: " + jobId);

            if (jobId == null || jobId.isBlank()) {
                continue;
            }

            Job job = jobRepository.findById(jobId)
                    .orElse(new Job());

            job.setJobId(jobId);
            job.setTitle(dto.getRecrutPbancTtl());
            job.setCompany(dto.getInstNm());
            job.setLocation(dto.getWorkRgnNmLst());
            job.setJobType(dto.getRecrutSeNm());
            job.setDeadline(parseDate(dto.getPbancEndYmd()));
            job.setCreatedAt(parseDate(dto.getPbancBgngYmd()));

            jobRepository.save(job);
        }

        System.out.println("공고 DB 저장 완료");
        System.out.println("API 전체 개수: " + jobs.size());
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
    }

    private boolean isEntry(JobDto job) {
        String recrutSe = job.getRecrutSeNm();
        return recrutSe != null && recrutSe.contains("신입");
    }

    private boolean isItByTitleOrNcs(JobDto job) {
        return containsItKeyword(job.getRecrutPbancTtl())
                || containsItKeyword(job.getNcsCdNmLst());
    }

    private boolean containsItKeyword(String text) {
        if (text == null) return false;

        String normalized = text.toLowerCase();

        for (String keyword : IT_KEYWORDS) {
            if (normalized.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotExpired(JobDto job) {
        String endDateStr = job.getPbancEndYmd();
        if (endDateStr == null) return false;

        LocalDate endDate =
                LocalDate.parse(endDateStr, DateTimeFormatter.BASIC_ISO_DATE);

        return !LocalDate.now().isAfter(endDate);
    }

    // 🔹 검색
    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContaining(keyword);
    }

    // 🔹 지역 필터
    public List<Job> filterByLocation(String location) {
        return jobRepository.findByLocation(location);
    }

    // 🔹 직무 필터
    public List<Job> filterByJobType(String jobType) {
        return jobRepository.findByJobType(jobType);
    }

    // 🔹 마감순
    public List<Job> sortByDeadline() {
        return jobRepository.findAllByOrderByDeadlineAsc();
    }

    // 🔹 최신순
    public List<Job> sortByLatest() {
        return jobRepository.findAllByOrderByCreatedAtDesc();
    }
}
