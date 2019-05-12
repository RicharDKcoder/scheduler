package com.docryze.scheduler.web.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class JobInfo {
    private String description;
    private String className;
}
