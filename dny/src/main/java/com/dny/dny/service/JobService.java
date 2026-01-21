package com.dny.dny.service;

import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class JobService {

    private static final String BASE_URL =
            "https://apis.data.go.kr/1051000/recruitment/list";

    private static final String SERVICE_KEY =
            "985c21b05bf528922cc657755c1fdc9854c0d0b94de3246b42315748f66a8acc";

    private static final String[] IT_KEYWORDS = {
            "정보", "전산", "it", "소프트웨어", "개발", "시스템",
            "데이터", "보안", "정보화", "개발자", "db", "웹",
            "서버", "네트워크", "sw", "프로그래밍", "정보통신", "ict"
    };

    public List<JobDto> getItJobs() {

        String url = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
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
                .filter(this::isItByNcs)
                .toList();
    }

    /**
     * 신입 또는 신입·경력
     */
    private boolean isEntry(JobDto job) {
        String recrutSe = job.getRecrutSeNm();
        return recrutSe != null && recrutSe.contains("신입");
    }

    /**
     * NCS 직무 분야 기준 IT 판별
     */
    private boolean isItByNcs(JobDto job) {
        String ncs = job.getNcsCdNmLst();
        if (ncs == null) return false;

        String normalized = ncs.toLowerCase();

        for (String keyword : IT_KEYWORDS) {
            if (normalized.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}