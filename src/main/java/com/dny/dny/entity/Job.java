package com.dny.dny.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Job {

    @Id
    private String jobId;

    private String title;
    private String company;
    private String location;
    private String jobType;   // 인턴 / 신입공채 구분

    private LocalDate deadline;   // 마감일
    private LocalDate createdAt;  // 등록일
}
