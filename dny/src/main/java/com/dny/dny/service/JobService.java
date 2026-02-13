package com.dny.dny.service;

import com.dny.dny.entity.Job;
import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import com.dny.dny.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

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
     * IT + 신입 + 접수중 공고 조회
     */
    public List<JobDto> getItJobs() {

        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 200)
                .queryParam("resultType", "json")
                .build(false)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        JobApiResponse response =
                restTemplate.getForObject(url, JobApiResponse.class);

        if (response == null || response.getResult() == null) {
            return List.of();
        }

        return response.getResult().stream()
                .filter(this::isEntry)
                .filter(this::isItByTitleOrNcs)
                .filter(this::isNotExpired)
                .toList();
    }
    public void saveJobsToDb() {
        List<JobDto> jobs = getItJobs();

        for (JobDto dto : jobs) {
            Job job = new Job();

            job.setJobId(dto.getRecrutPbancSn());
            job.setTitle(dto.getRecrutPbancTtl());
            job.setCompany(dto.getInstNm());
            job.setLocation(dto.getWorkRgnNmLst());
            job.setJobType(dto.getRecrutSeNm());

            job.setDeadline(parseDate(dto.getPbancEndYmd()));
            job.setCreatedAt(parseDate(dto.getPbancBgngYmd()));

            jobRepository.save(job);
        }
    } // DB 저장 메서드

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null) return null;
        return LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
    } // 날짜 변환 메서드

    /** 신입 또는 신입·경력 */
    private boolean isEntry(JobDto job) {
        String recrutSe = job.getRecrutSeNm();
        return recrutSe != null && recrutSe.contains("신입");
    }

    /** IT 직무 판단 (제목 OR NCS) */
    private boolean isItByTitleOrNcs(JobDto job) {
        return containsItKeyword(job.getRecrutPbancTtl())
                || containsItKeyword(job.getNcsCdNmLst());
    }

    /** IT 키워드 포함 여부 */
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

    /** 접수 마감 여부 (오늘 이전 마감 제외) */
    private boolean isNotExpired(JobDto job) {
        String endDateStr = job.getPbancEndYmd();
        if (endDateStr == null) return false;

        LocalDate endDate =
                LocalDate.parse(endDateStr, DateTimeFormatter.BASIC_ISO_DATE);

        return !LocalDate.now().isAfter(endDate);
    }


    // 키워드 검색
    public List<Job> searchJobs(String keyword) {
        return jobRepository.findByTitleContaining(keyword);
    }

    // 지역 필터
    public List<Job> filterByLocation(String location) {
        return jobRepository.findByLocation(location);
    }

    // 채용구분 필터
    public List<Job> filterByJobType(String jobType) {
        return jobRepository.findByJobType(jobType);
    }

    // 마감일 임박순
    public List<Job> sortByDeadline() {
        return jobRepository.findAllByOrderByDeadlineAsc();
    }

    // 최신순
    public List<Job> sortByLatest() {
        return jobRepository.findAllByOrderByCreatedAtDesc();
    }





    }

