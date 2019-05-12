package com.docryze.scheduler.core.bean;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
public class JobStatus {
    private String jobName;
    private String groupName;
    private String triggerName;

    private String jobClassName;
    private String description;
    private Date preFireTime;
    private Date nextFireTime;
}
