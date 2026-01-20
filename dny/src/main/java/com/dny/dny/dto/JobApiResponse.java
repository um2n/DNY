package com.dny.dny.dto;

import java.util.List;

public class JobApiResponse {

    private List<JobDto> result;

    public List<JobDto> getResult() {
        return result;
    }

    public void setResult(List<JobDto> result) {
        this.result = result;
    }
}
