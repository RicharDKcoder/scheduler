package com.docryze.scheduler.web.Service;

import com.docryze.scheduler.core.bean.JobStatus;
import com.docryze.scheduler.core.component.JobContext;
import com.docryze.scheduler.core.component.monitor.JobMonitor;
import com.docryze.scheduler.web.vo.JobInfo;
import com.docryze.scheduler.web.vo.JobTriggerDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobMonitor jobMonitor;


    public List<JobInfo> listJob(){
        List<JobContext> jobContextList = jobMonitor.listJob(false);
        if(CollectionUtils.isEmpty(jobContextList)){
            return null;
        }
        return jobContextList.stream().map(jobContext ->
                    JobInfo.builder()
                            .className(jobContext.getName())
                            .description(jobContext.getDescription())
                            .build())
                .collect(Collectors.toList());
    }

    public List<JobTriggerDetail> listJobStatus(String jobClassName) throws SchedulerException {
        List<JobStatus> jobStatusList = jobMonitor.listJobStatus(jobClassName);

        return jobStatusList.stream().map(jobStatus ->
                    JobTriggerDetail.builder()
                            .description(jobStatus.getDescription())
                            .groupName(jobStatus.getGroupName())
                            .jobName(jobStatus.getJobName())
                            .triggerName(jobStatus.getTriggerName())
                            .jobClassName(jobStatus.getJobClassName())
                            .nextFireTime(jobStatus.getNextFireTime())
                            .preFireTime(jobStatus.getPreFireTime())
                            .build())
                .collect(Collectors.toList());
    }
}
