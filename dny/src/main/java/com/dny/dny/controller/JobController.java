package com.dny.dny.controller;

import com.dny.dny.dto.JobDto;
import com.dny.dny.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public List<JobDto> getJobs() throws Exception {
        return jobService.getJobs();
    }
}
