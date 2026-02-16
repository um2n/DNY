package com.dny.dny.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class JobApiResponse {

    private int resultCode;
    private String resultMsg;
    private int totalCount;
    private List<JobDto> result;
}
