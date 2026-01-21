package com.dny.dny.controller;

import com.dny.dny.dto.JobDto;
import com.dny.dny.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
