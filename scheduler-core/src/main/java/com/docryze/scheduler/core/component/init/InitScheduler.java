package com.docryze.scheduler.core.component.init;

import com.docryze.scheduler.core.bean.JobConfig;
import com.docryze.scheduler.core.component.JobBeanManager;
import com.docryze.scheduler.core.component.JobContext;
import com.docryze.scheduler.core.exception.SchedulerCoreException;
import com.docryze.scheduler.core.listener.DefaultJobListener;
import com.docryze.scheduler.core.util.SchedulerUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static org.quartz.JobKey.jobKey;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;
import static org.quartz.impl.matchers.OrMatcher.or;

@Slf4j
@Component
public class InitScheduler {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ListenerManager listenerManager;
    @Value("${scheduler.core.component.init.init-scheduler.cron-key:cron}")
    private String cronKey;


    @PostConstruct
    public void initScheduler() throws SchedulerCoreException, SchedulerException {
        List<JobContext> jobContexts = JobBeanManager.getJobClassList();
        for(JobContext jobContext : jobContexts){//所有 已定义的job

            List<Map<String,Object>> jobParamList = jobContext.queryJobParamList();
            boolean hasListener = false;
            for (Map<String,Object> jobParamMap : jobParamList){
                //根据参数的不同 创建不同的任务
                String cron = (String) jobParamMap.get(cronKey);
                JobConfig jobConfig = SchedulerUtil.schedulerJob(scheduler,jobContext,cron,jobParamMap);
                log.info("scheduler the job succeed！ jobContext:{}, cron:{}, jobParamMap:{}",jobContext,cron,jobParamMap);

                if(!hasListener){
                    String jobName = jobConfig.getJobName();
                    String groupName = jobConfig.getGroupName();
                    listenerManager.addJobListener(new DefaultJobListener(jobContext),
                            KeyMatcher.keyEquals(jobKey(jobName, groupName)));
                    hasListener = true;
                }
            }
        }
    }
}
