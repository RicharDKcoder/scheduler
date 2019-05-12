package com.docryze.scheduler.core.component.daemon;

import com.docryze.scheduler.core.annotation.SchedulerJob;
import com.docryze.scheduler.core.component.JobContext;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SchedulerJob
public class JobScan extends JobContext {
    @Value("${scheduler.core.component.init.init-scheduler.cron-key:cron}")
    private String cronKey;
    @Value("${scheduler.core.component.daemon.job-scan.cron:0/10 * * * * ?}")
    private String cron;


    public JobScan() {
        this.setDescription("Job Scan 扫描任务");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("this is jobScan cronKey:{}, cron:{}",cronKey,cron);
    }


    @Override
    public List<Map<String, Object>> queryJobParamList() {
        Map<String,Object> map = new HashMap<>();
        map.put(cronKey,cron);
        return new ArrayList<Map<String, Object>>(){{add(map);}};
    }
}
