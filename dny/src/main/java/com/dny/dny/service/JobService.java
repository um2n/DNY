package com.dny.dny.service;

import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class JobService {

    private static final String BASE_URL =
            "https://apis.data.go.kr/1051000/recruitment/list";

    private static final String SERVICE_KEY =
            "985c21b05bf528922cc657755c1fdc9854c0d0b94de3246b42315748f66a8acc";

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
                .queryParam("serviceKey", SERVICE_KEY)
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
}
