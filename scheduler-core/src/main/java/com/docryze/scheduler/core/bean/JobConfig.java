package com.docryze.scheduler.core.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class JobConfig {
    private String jobName;
    private String groupName;
    private String triggerName;
    private String cron;
    private String description;
}
