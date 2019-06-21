package com.docryze.scheduler.core.component;

import com.docryze.scheduler.core.util.SchedulerUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Command {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 执行任务
     * @param jobClassName
     * @param unique
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public void execute(String jobClassName,String unique) throws SchedulerException, ClassNotFoundException {

        Object obj = applicationContext.getBean(Class.forName(jobClassName));

        if(obj instanceof JobContext){
            JobContext jobContext = (JobContext)obj;
            Map<String,Object> paramsMap = jobContext.queryJobParam(unique);
            SchedulerUtil.executeNow(scheduler,jobContext,paramsMap);
        }
    }
}
