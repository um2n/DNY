package com.dny.dny.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class JobService {

    public String getJobs() {

        String baseUrl = "https://apis.data.go.kr/1051000/recruitment/list";
        String serviceKey = "985c21b05bf528922cc657755c1fdc9854c0d0b94de3246b42315748f66a8acc";

        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("type", "json")
                .build(false)   // ★ 이 API는 false가 맞다
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
