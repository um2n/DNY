package com.dny.dny.service;

import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import com.dny.dny.dto.JobResponseDto;
import com.dny.dny.entity.Job;
import com.dny.dny.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String BASE_URL =
            "https://apis.data.go.kr/1051000/recruitment/list";

    @Value("${job.api.key}")
    private String serviceKey;

    /* DB 공고 조회 + 북마크 여부 포함 */
    public List<JobResponseDto> getJobsWithBookmark(Long userId) {

        List<Job> jobs = jobRepository.findAll();
        Set<String> bookmarkedIds =
                bookmarkService.getBookmarkedJobIds(userId);

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
                .toList();
    }

    /* API 호출 (페이지 제한 방식) */
    public List<JobDto> getItJobs() {

        RestTemplate restTemplate = new RestTemplate();
        List<JobDto> allJobs = new ArrayList<>();

        int maxPage = 20;   // 필요 시 조절
        int size = 100;

        for (int page = 1; page <= maxPage; page++) {

            String url = UriComponentsBuilder
                    .fromUriString(BASE_URL)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", page)
                    .queryParam("numOfRows", size)
                    .queryParam("resultType", "json")
                    .build()
                    .toUriString();

            JobApiResponse response =
                    restTemplate.getForObject(url, JobApiResponse.class);

            if (response == null || response.getResult() == null) {
                break;
            }

            allJobs.addAll(response.getResult());
        }

        return allJobs.stream()
                .filter(this::isItNewbieJob)
                .toList();
    }

    /* 서버 시작 시 DB 저장 */
    @Transactional
    public void saveJobsToDb() {

        List<JobDto> apiJobs = getItJobs();
        if (apiJobs.isEmpty()) return;

        Set<String> existingIds =
                new HashSet<>(jobRepository.findAllJobIds());

        List<Job> newJobs = new ArrayList<>();

        for (JobDto dto : apiJobs) {

            String jobId = dto.getRecrutPblntSn();
            if (jobId == null || jobId.isBlank()) continue;
            if (existingIds.contains(jobId)) continue;

            newJobs.add(convertToEntity(dto));
        }

        if (!newJobs.isEmpty()) {
            jobRepository.saveAll(newJobs);
        }

        System.out.println("신규 저장 개수: " + newJobs.size());
    }

    /* IT + 신입 필터 */
    private boolean isItNewbieJob(JobDto job) {

        String title = job.getRecrutPbancTtl();
        String type = job.getRecrutSeNm();

        if (title == null || type == null) return false;

        if (!(type.contains("신입"))) {
            return false;
        }

        String lower = title.toLowerCase();

        // 반드시 직무 단위 IT 키워드 포함
        if (!(lower.contains("전산")
                || lower.contains("정보보안")
                || lower.contains("정보보호")
                || lower.contains("네트워크")
                || lower.contains("시스템")
                || lower.contains("서버")
                || lower.contains("it ")
                || lower.contains("ict")
                || lower.contains("정보기술")
                || lower.contains("사이버보안"))) {
            return false;
        }

        // 제외 키워드
        if (lower.contains("연구")
                || lower.contains("관리")
                || lower.contains("AMI")
                || lower.contains("정비")
                || lower.contains("운전")
                || lower.contains("간호")
                || lower.contains("환경")
                || lower.contains("미화")
                || lower.contains("배전")
                || lower.contains("전력")
                || lower.contains("기계")) {
            return false;
        }

        return true;
    }


    /* DTO → Entity 변환 */
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

    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContaining(keyword);
    }

    public List<Job> filterByLocation(String location) {
        return jobRepository.findByLocation(location);
    }

    public List<Job> filterByJobType(String jobType) {
        return jobRepository.findByJobType(jobType);
    }

    public List<Job> sortByDeadline() {
        return jobRepository.findAllByOrderByDeadlineAsc();
    }

    public List<Job> sortByLatest() {
        return jobRepository.findAllByOrderByCreatedAtDesc();
    }

    private LocalDate parseDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.isBlank()) return null;
            return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            return null;
        }
    }
}
