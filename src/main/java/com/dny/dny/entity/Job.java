package com.dny.dny.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 채용 공고 정보 엔티티
 */
@Entity
@Getter
@Setter
public class Job {

    @Id
    private String jobId;   // 고유 식별자

    private String title;    // 제목
    private String company;  // 기관명
    private String location; // 근무지
    private String jobType;  // 채용 구분

    private LocalDate deadline;  // 마감 기한
    private LocalDate createdAt; // 등록 일자
}
