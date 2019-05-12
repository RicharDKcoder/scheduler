package com.docryze.scheduler.core.util;

import com.docryze.scheduler.core.bean.JobConfig;
import com.docryze.scheduler.core.bean.JobStatus;
import com.docryze.scheduler.core.component.JobContext;
import com.docryze.scheduler.core.exception.SchedulerCoreException;
import com.docryze.scheduler.core.exception.SchedulerCoreException.ExceptionCode;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;

import java.util.*;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

public class SchedulerUtil {

    /**
     * 创建调度
     * @param scheduler
     * @param jobContext
     * @param cron
     * @param paramsMap
     * @return
     * @throws SchedulerException
     * @throws SchedulerCoreException
     */
    public static JobConfig schedulerJob(Scheduler scheduler, JobContext jobContext, String cron, Map<String,Object> paramsMap) throws SchedulerException, SchedulerCoreException {
        Class<? extends JobContext> jobClass = jobContext.getClass();
        if(jobClass == null) throw new SchedulerCoreException(ExceptionCode.PARAMS_CLASS_ERROR);
        if(cron == null) throw new SchedulerCoreException(ExceptionCode.PARAMS_CRON_ERROR);

        String jobClassName = jobClass.getName();
        String description = jobContext.getDescription();

        String jobName = generateJobName(jobClassName);
        String groupName = generateGroupName(jobClassName);
        String triggerName = generateTriggerName(jobClassName);

        JobDetail job = newJob(jobClass)
                .withIdentity(jobName, groupName)
                .usingJobData(new JobDataMap(paramsMap))
                .build();

        CronTrigger trigger = newTrigger()
                .withDescription(description)
                .withIdentity(triggerName, groupName)
                .withSchedule(cronSchedule(cron))
                .build();

        scheduler.scheduleJob(job, trigger);

        return JobConfig.builder()
                .cron(cron)
                .jobName(jobName)
                .triggerName(triggerName)
                .groupName(groupName)
                .description(description)
                .build();
    }


    /**
     * 移除调度
     * @param scheduler
     * @param jobContext
     * @throws SchedulerException
     */
    public static void unscheduleJob(Scheduler scheduler, JobContext jobContext) throws SchedulerException {
        Class<? extends JobContext> jobClass = jobContext.getClass();
        String jobClassName = jobClass.getName();
        String triggerName = generateTriggerName(jobClassName);
        String groupName = generateGroupName(jobClassName);

        scheduler.unscheduleJob(triggerKey(triggerName, groupName));
    }


    /**
     * 更新调度
     * @param scheduler
     * @param jobContext
     * @param cron
     * @param paramsMap
     * @throws SchedulerException
     */
    public static void rescheduleJob(Scheduler scheduler, JobContext jobContext, String cron,Map<String,Object> paramsMap) throws SchedulerException {
        Class<? extends JobContext> jobClass = jobContext.getClass();
        String jobClassName = jobClass.getName();
        String triggerName = generateTriggerName(jobClassName);
        String groupName = generateGroupName(jobClassName);

        Trigger oldTrigger = scheduler.getTrigger(triggerKey(triggerName, groupName));
        TriggerBuilder tb = oldTrigger.getTriggerBuilder();
        Trigger newTrigger = tb.withSchedule(cronSchedule(cron))
                .usingJobData(new JobDataMap(paramsMap))
                .build();

        scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
    }


    /**
     * 立即执行
     * @param scheduler
     * @param jobContext
     * @param paramsMap
     * @throws SchedulerException
     */
    public static void executeNow(Scheduler scheduler,JobContext jobContext,Map<String,Object> paramsMap) throws SchedulerException {
        Class<? extends JobContext> jobClass = jobContext.getClass();
        String jobClassName = jobClass.getName();
        JobKey jobKey = new JobKey(generateJobName(jobClassName),generateGroupName(jobClassName));
        scheduler.triggerJob(jobKey,new JobDataMap(paramsMap));
    }


    public static List<JobDetail> listJobDetails(Scheduler scheduler, String groupName) throws SchedulerException {
        List<JobDetail> jobList = new ArrayList<>();

        if(StringUtils.isNoneBlank(groupName)){
            Set<JobKey> jobKeySet = scheduler.getJobKeys(groupEquals(groupName));
            for(JobKey jobKey : jobKeySet){
                jobList.add(scheduler.getJobDetail(jobKey));
            }
        }else{
            for(String group : scheduler.getJobGroupNames()){
                Set<JobKey> jobKeySet = scheduler.getJobKeys(groupEquals(group));
                for(JobKey jobKey : jobKeySet){
                    jobList.add(scheduler.getJobDetail(jobKey));
                }
            }
        }

        return jobList;
    }


    public static List<Trigger> listTriggerDetails(Scheduler scheduler, String groupName) throws SchedulerException {
        List<Trigger> triggerList = new ArrayList<>();


        if(StringUtils.isNoneBlank(groupName)){
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupEquals(groupName));
            for(TriggerKey triggerKey : triggerKeySet){
                triggerList.add(scheduler.getTrigger(triggerKey));
            }
        }else{
            for(String group: scheduler.getTriggerGroupNames()) {
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupEquals(group));
                for(TriggerKey triggerKey : triggerKeySet){
                    triggerList.add(scheduler.getTrigger(triggerKey));
                }
            }
        }
        return triggerList;
    }


    public static List<JobStatus> listJobStatus(Scheduler scheduler, String groupName) throws SchedulerException {
        if(StringUtils.isNoneBlank(groupName)){
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupEquals(groupName));
            return queryTrggerJobStatus(scheduler, triggerKeySet);

        }

        List<JobStatus> jobStatusList = new ArrayList<>();
        for(String group: scheduler.getTriggerGroupNames()) {
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupEquals(group));
            jobStatusList.addAll(queryTrggerJobStatus(scheduler,triggerKeySet));
        }

        return jobStatusList;
    }

    private static List<JobStatus> queryTrggerJobStatus(Scheduler scheduler, Set<TriggerKey> triggerKeySet) throws SchedulerException {
        List<JobStatus> jobStatusList = new ArrayList<>();
        for(TriggerKey triggerKey : triggerKeySet){
            Trigger trigger = scheduler.getTrigger(triggerKey);
            String description = trigger.getDescription();      //描述
            Date preFireTime = trigger.getPreviousFireTime();   //上一次触发时间
            Date nextFireTime = trigger.getNextFireTime();      //下次触发时间

            String triggerKeyGroup = triggerKey.getGroup();
            String triggerKeyName = triggerKey.getName();
            Set<JobKey> jobKeySet = scheduler.getJobKeys(groupEquals(triggerKeyGroup));

            for(JobKey jobKey : jobKeySet){
                String jobName = jobKey.getName();
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                Class<? extends Job> jobClass = jobDetail.getJobClass();
                String jobClassName = jobClass.getName();

                jobStatusList.add(JobStatus.builder()
                        .triggerName(triggerKeyName)
                        .groupName(triggerKeyGroup)
                        .jobName(jobName)
                        .jobClassName(jobClassName)
                        .description(description)
                        .preFireTime(preFireTime)
                        .nextFireTime(nextFireTime)
                        .build());
            }
        }

        return jobStatusList;
    }


    private static String generateJobName(String str) {
        return "JOB-" + str;
    }

    private static String generateTriggerName(String str) {
        return "TRIGGER-" + str;
    }

    private static String generateGroupName(String str){
        return "GROUP-" + str;
    }
}
