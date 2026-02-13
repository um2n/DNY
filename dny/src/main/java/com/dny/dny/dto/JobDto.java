package com.dny.dny.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JobDto {

    private String instNm;          // 기관명
    private String recrutPbancTtl;  // 채용공고 제목
    private String pbancBgngYmd;    // 공고 시작일
    private String pbancEndYmd;     // 공고 마감일
    private String srcUrl;          // 공고 링크
    private String workRgnNmLst;    // 지역
    private String recrutNope;      // 인원
    private String recrutPbancSn;
    private String recrutSeNm;      // 신입/경력 구분
    private String ncsCdNmLst;      // NCS 직무
    private String hireTypeNmLst;

}