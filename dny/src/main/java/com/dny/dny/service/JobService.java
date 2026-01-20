package com.dny.dny.service;

import com.dny.dny.dto.JobDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Service
public class JobService {

    public List<JobDto> getJobs() throws Exception {

        String baseUrl = "https://apis.data.go.kr/1051000/recruitment/list";
        String serviceKey = "a220e2b32572f4ab1a598e9556bf649adbbe57b3a95b5c1b84ec4c9a7f01973f";


        String url = UriComponentsBuilder
                .fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("type", "json")
                .build(false)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);

        JsonNode items = root
                .path("response")
                .path("body")
                .path("items")
                .path("item");

        List<JobDto> result = new ArrayList<>();

        for (JsonNode item : items) {
            JobDto dto = new JobDto();

            dto.setInstNm(item.path("instNm").asText());
            dto.setNcsCdNmLst(item.path("ncsCdNmLst").asText());
            dto.setHireTypeNmLst(item.path("hireTypeNmLst").asText());
            dto.setWorkRgnNmLst(item.path("workRgnNmLst").asText());
            dto.setRecrutSeNm(item.path("recrutSeNm").asText());
            dto.setRecrutPbancTtl(item.path("recrutPbancTtl").asText());
            dto.setPbancBgngYmd(item.path("pbancBgngYmd").asText());
            dto.setPbancEndYmd(item.path("pbancEndYmd").asText());
            dto.setRecrutNope(item.path("recrutNope").asInt());
            dto.setSrcUrl(item.path("srcUrl").asText());

            result.add(dto);
        }

        return result;
    }
}
