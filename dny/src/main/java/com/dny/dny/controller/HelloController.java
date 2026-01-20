package com.dny.dny.controller;

import com.dny.dny.service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final JobService jobService;

    public HelloController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public String jobs() {
        return jobService.getJobs();
    }
}
