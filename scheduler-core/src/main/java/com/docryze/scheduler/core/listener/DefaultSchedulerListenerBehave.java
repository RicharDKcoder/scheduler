package com.docryze.scheduler.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
public class DefaultSchedulerListenerBehave {
    public void jobScheduled(Trigger trigger) {
    }

    public void jobUnscheduled(TriggerKey triggerKey) {

    }

    public void triggerFinalized(Trigger trigger) {

    }

    public void triggerPaused(TriggerKey triggerKey) {

    }

    public void triggersPaused(String triggerGroup) {

    }

    public void triggerResumed(TriggerKey triggerKey) {

    }

    public void triggersResumed(String triggerGroup) {

    }

    public void jobAdded(JobDetail jobDetail) {

    }

    public void jobDeleted(JobKey jobKey) {

    }

    public void jobPaused(JobKey jobKey) {

    }

    public void jobsPaused(String jobGroup) {

    }

    public void jobResumed(JobKey jobKey) {

    }

    public void jobsResumed(String jobGroup) {

    }

    public void schedulerError(String msg, SchedulerException cause) {
        log.error("scheduler error ! msg:{}",msg,cause);
    }

    public void schedulerInStandbyMode() {

    }

    public void schedulerStarted() {

    }

    public void schedulerStarting() {

    }

    public void schedulerShutdown() {

    }

    public void schedulerShuttingdown() {

    }

    public void schedulingDataCleared() {

    }


}
