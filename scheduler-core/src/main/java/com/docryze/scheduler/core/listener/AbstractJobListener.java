package com.docryze.scheduler.core.listener;

import com.docryze.scheduler.core.component.JobContext;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public abstract class AbstractJobListener implements JobListener {
    private JobContext jobContext;


    public AbstractJobListener(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public String getName() {
        return jobContext.getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        jobContext.jobToBeExecuted(context);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        jobContext.jobExecutionVetoed(context);

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        jobContext.jobWasExecuted(context, jobException);
    }

}
