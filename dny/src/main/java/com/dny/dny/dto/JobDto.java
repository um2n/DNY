package com.dny.dny.dto;

public class JobDto {
    private String recrutPbancTtl;    // 채용공고 제목
    private String instNm;           // 기관명
    private String hireTypeNmLst;     // 채용 형태
    private String recrutSeNm;      // 채용 구분
    private int recrutNope;        // 채용 인원
    private String ncsCdNmLst;        // 직무 분야
    private String workRgnNmLst;      // 근무 지역
    private String pbancBgngYmd;      // 접수 시작일
    private String pbancEndYmd;       // 접수 마감일

    public String getRecrutPbancTtl() {
        return recrutPbancTtl;
    }

    public void setRecrutPbancTtl(String recrutPbancTtl) {
        this.recrutPbancTtl = recrutPbancTtl;
    }

    public String getInstNm() {
        return instNm;
    }

    public void setInstNm(String instNm) {
        this.instNm = instNm;
    }

    public String getHireTypeNmLst() {
        return hireTypeNmLst;
    }

    public void setHireTypeNmLst(String hireTypeNmLst) {
        this.hireTypeNmLst = hireTypeNmLst;
    }

    public String getRecrutSeNm() {
        return recrutSeNm;
    }

    public void setRecrutSeNm(String recrutSeNm) {
        this.recrutSeNm = recrutSeNm;
    }

    public int getRecrutNope() {
        return recrutNope;
    }

    public void setRecrutNope(int recrutNope) {
        this.recrutNope = recrutNope;
    }

    public String getNcsCdNmLst() {
        return ncsCdNmLst;
    }

    public void setNcsCdNmLst(String ncsCdNmLst) {
        this.ncsCdNmLst = ncsCdNmLst;
    }

    public String getWorkRgnNmLst() {
        return workRgnNmLst;
    }

    public void setWorkRgnNmLst(String workRgnNmLst) {
        this.workRgnNmLst = workRgnNmLst;
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

    private String srcUrl;            // 공고 링크
}
