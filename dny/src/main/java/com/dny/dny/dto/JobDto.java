package com.dny.dny.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDto {

    @JsonProperty("recrutPblntSn")
    private String recrutPblntSn;

    @JsonProperty("recrutPbancTtl")
    private String recrutPbancTtl;

    @JsonProperty("instNm")
    private String instNm;

    @JsonProperty("workRgnNmLst")
    private String workRgnNmLst;

    @JsonProperty("recrutSeNm")
    private String recrutSeNm;

    @JsonProperty("pbancBgngYmd")
    private String pbancBgngYmd;

    @JsonProperty("pbancEndYmd")
    private String pbancEndYmd;

    @JsonProperty("ncsCdNmLst")
    private String ncsCdNmLst;
}
