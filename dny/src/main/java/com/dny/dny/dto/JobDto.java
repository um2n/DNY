package com.dny.dny.dto;

public class JobDto {

    private String instNm;          // 기관명
    private String recrutPbancTtl;  // 채용공고 제목
    private String pbancBgngYmd;    // 공고 시작일
    private String pbancEndYmd;     // 공고 마감일
    private String srcUrl;          // 공고 링크
    private String workRgnNmLst;    // 지역
    private String recrutNope;      // 인원

    public String getRecrutNope() {
        return recrutNope;
    }

    public void setRecrutNope(String recrutNope) {
        this.recrutNope = recrutNope;
    }

    public String getRecrutSeNm() {
        return recrutSeNm;
    }

    public void setRecrutSeNm(String recrutSeNm) {
        this.recrutSeNm = recrutSeNm;
    }

    public String getNcsCdNmLst() {
        return ncsCdNmLst;
    }

    public void setNcsCdNmLst(String ncsCdNmLst) {
        this.ncsCdNmLst = ncsCdNmLst;
    }

    public String getHireTypeNmLst() {
        return hireTypeNmLst;
    }

    public void setHireTypeNmLst(String hireTypeNmLst) {
        this.hireTypeNmLst = hireTypeNmLst;
    }

    private String recrutSeNm;      // 직무
    private String ncsCdNmLst;      // 경력
    private String hireTypeNmLst;   // 채용구분

    // getter / setter
    public String getInstNm() {
        return instNm;
    }

    public void setInstNm(String instNm) {
        this.instNm = instNm;
    }

    public String getRecrutPbancTtl() {
        return recrutPbancTtl;
    }

    public String getWorkRgnNmLst() {
        return workRgnNmLst;
    }

    public void setWorkRgnNmLst(String workRgnNmLst) {
        this.workRgnNmLst = workRgnNmLst;
    }

    public void setRecrutPbancTtl(String recrutPbancTtl) {
        this.recrutPbancTtl = recrutPbancTtl;
    }

    public String getPbancBgngYmd() {
        return pbancBgngYmd;
    }

    public void setPbancBgngYmd(String pbancBgngYmd) {
        this.pbancBgngYmd = pbancBgngYmd;
    }

    public String getPbancEndYmd() {
        return pbancEndYmd;
    }

    public void setPbancEndYmd(String pbancEndYmd) {
        this.pbancEndYmd = pbancEndYmd;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }
}