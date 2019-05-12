package com.docryze.scheduler.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
public abstract class AbstractSchedulerListener implements SchedulerListener {
    private DefaultSchedulerListenerBehave schedulerListenerBehave;

    public AbstractSchedulerListener(DefaultSchedulerListenerBehave schedulerListenerBehave) {
        this.schedulerListenerBehave = schedulerListenerBehave;
    }

    @Override
    public void jobScheduled(Trigger trigger) {
        schedulerListenerBehave.jobScheduled(trigger);
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        schedulerListenerBehave.jobUnscheduled(triggerKey);
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        schedulerListenerBehave.triggerFinalized(trigger);
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        schedulerListenerBehave.triggerPaused(triggerKey);
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        schedulerListenerBehave.triggersPaused(triggerGroup);
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        schedulerListenerBehave.triggerResumed(triggerKey);
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        schedulerListenerBehave.triggersResumed(triggerGroup);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        schedulerListenerBehave.jobAdded(jobDetail);
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        schedulerListenerBehave.jobDeleted(jobKey);
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        schedulerListenerBehave.jobPaused(jobKey);
    }

    @Override
    public void jobsPaused(String jobGroup) {
        schedulerListenerBehave.jobsPaused(jobGroup);
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        schedulerListenerBehave.jobResumed(jobKey);
    }

    @Override
    public void jobsResumed(String jobGroup) {
        schedulerListenerBehave.jobsResumed(jobGroup);
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        schedulerListenerBehave.schedulerError(msg, cause);
    }

    @Override
    public void schedulerInStandbyMode() {
        schedulerListenerBehave.schedulerInStandbyMode();
    }

    @Override
    public void schedulerStarted() {
        schedulerListenerBehave.schedulerStarted();
    }

    @Override
    public void schedulerStarting() {
        schedulerListenerBehave.schedulerStarting();
    }

    @Override
    public void schedulerShutdown() {
        schedulerListenerBehave.schedulerShutdown();
    }

    @Override
    public void schedulerShuttingdown() {
        schedulerListenerBehave.schedulerShuttingdown();
    }

    @Override
    public void schedulingDataCleared() {
        schedulerListenerBehave.schedulingDataCleared();
    }
}
