package com.dny.dny.controller;

import com.dny.dny.dto.JobDto;
import com.dny.dny.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import com.dny.dny.entity.Job;


@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/it")
    public List<JobDto> getItJobs() {
        return jobService.getItJobs();
    }

    // 검색
    @GetMapping("/search")
    public List<Job> search(@RequestParam String keyword) {
        return jobService.searchJobs(keyword);
    }

    // 지역 필터
    @GetMapping("/location")
    public List<Job> filterByLocation(@RequestParam String location) {
        return jobService.filterByLocation(location);
    }

    // 채용구분 필터
    @GetMapping("/type")
    public List<Job> filterByType(@RequestParam String jobType) {
        return jobService.filterByJobType(jobType);
    }

    // 마감임박순
    @GetMapping("/deadline")
    public List<Job> sortByDeadline() {
        return jobService.sortByDeadline();
    }

    // 최신순
    @GetMapping("/latest")
    public List<Job> sortByLatest() {
        return jobService.sortByLatest();
    }
}
