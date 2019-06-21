package com.docryze.scheduler.core.component.monitor;

import com.docryze.scheduler.core.bean.JobStatus;
import com.docryze.scheduler.core.component.JobBeanManager;
import com.docryze.scheduler.core.component.JobContext;
import com.docryze.scheduler.core.util.SchedulerUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMonitor {
    @Autowired
    private Scheduler scheduler;

    /**
     * 所有 JobContext
     * @param init  是否是内置job
     * @return
     */
    public List<JobContext> listJob(Boolean init){
        List<JobContext> jobContextList =  JobBeanManager.getJobClassList();
        if(init != null){
            String initPrefix = "com.docryze.scheduler.core.component.daemon";
            if(init){
                return jobContextList.stream().filter(jobContext -> {
                    String className = jobContext.getName();
                    return StringUtils.startsWith(className,initPrefix);
                }).collect(Collectors.toList());
            }else{
                return jobContextList.stream().filter(jobContext -> {
                    String className = jobContext.getName();
                    return !StringUtils.startsWith(className,initPrefix);
                }).collect(Collectors.toList());
            }
        }
        return jobContextList;
    }

    /**
     * 当前任务状态
     * @param jobClassName
     * @return
     * @throws SchedulerException
     */
    public List<JobStatus> listJobStatus(String jobClassName) throws SchedulerException {
        return SchedulerUtil.listJobStatus(scheduler, jobClassName);
    }

}
