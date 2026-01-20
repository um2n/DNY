package com.dny.dny.dto;

public class JobDto {

    private String instNm;          // 기관명
    private String recrutPbancTtl;  // 채용공고 제목
    private String pbancBgngYmd;    // 공고 시작일
    private String pbancEndYmd;     // 공고 마감일
    private String srcUrl;          // 공고 링크

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