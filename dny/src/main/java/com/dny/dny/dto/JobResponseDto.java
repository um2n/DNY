package com.dny.dny.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class JobResponseDto {

    private String jobId;
    private String title;
    private String company;
    private String location;
    private String jobType;
    private LocalDate deadline;
    private LocalDate createdAt;
    private boolean bookmarked;
}
