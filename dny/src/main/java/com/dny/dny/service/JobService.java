package com.dny.dny.service;

import com.dny.dny.dto.JobApiResponse;
import com.dny.dny.dto.JobDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    public List<JobDto> getJobs() {

        String baseUrl = "https://apis.data.go.kr/1051000/recruitment/list";
        String serviceKey = "985c21b05bf528922cc657755c1fdc9854c0d0b94de3246b42315748f66a8acc"; // 나중에 환경변수로 빼도 됨

        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("resultType", "json")
                .build(false)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();

        JobApiResponse response =
                restTemplate.getForObject(url, JobApiResponse.class);

        if (response == null || response.getResult() == null) {
            return List.of(); // 빈 리스트 반환
        }

        return response.getResult();

    }

    public List<JobDto> getItEntryJobs(List<JobDto> jobs) {
        return jobs.stream()
                .filter(this::isItJob)
                .filter(this::isEntryOrEntryAndCareer)
                .toList();
    }

    private boolean isItJob(JobDto job) {
        // TODO: ncsCdNmLst 기준 IT 직무 판단
        return true;
    }


    private boolean isEntryOrEntryAndCareer(JobDto job) {
        String recrutSe = job.getRecrutSeNm();
        if (recrutSe == null) return false;
        return recrutSe.contains("신입");
    }

    public List<JobDto> getItJobs() {
        return new ArrayList<>();
    }


}
