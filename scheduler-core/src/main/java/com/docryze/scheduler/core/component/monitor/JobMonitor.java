package com.docryze.scheduler.core.component.monitor;

import com.docryze.scheduler.core.bean.JobStatus;
import com.docryze.scheduler.core.component.JobContext;
import com.docryze.scheduler.core.util.SchedulerUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JobMonitor {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 当前任务状态
     * @param groupName
     * @return
     * @throws SchedulerException
     */
    public List<JobStatus> listJobStatus(String groupName) throws SchedulerException {
        List<JobStatus> jobStatusList = SchedulerUtil.listJobStatus(scheduler, groupName);
        //to something
        return jobStatusList;
    }


    /**
     * 立即执行任务
     * @param jobClassName
     * @param unique
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public void executeNow(String jobClassName,String unique) throws SchedulerException, ClassNotFoundException {

        Object obj = applicationContext.getBean(Class.forName(jobClassName));

        if(obj instanceof JobContext){
            JobContext jobContext = (JobContext)obj;
            Map<String,Object> paramsMap = jobContext.queryJobParam(unique);
            SchedulerUtil.executeNow(scheduler,jobContext,paramsMap);
        }

    }

}
