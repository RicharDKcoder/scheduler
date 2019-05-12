package com.docryze.scheduler.web.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Builder
@Getter
@ToString
public class JobTriggerDetail {
    private String jobName;
    private String groupName;
    private String triggerName;

    private String jobClassName;
    private String description;
    private Date preFireTime;
    private Date nextFireTime;
}
