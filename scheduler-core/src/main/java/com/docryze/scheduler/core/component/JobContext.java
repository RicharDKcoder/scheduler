package com.docryze.scheduler.core.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.aop.support.AopUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Setter
@Getter
@ToString
public abstract class JobContext implements Job {
    private String description;


    /**
     * 获取 job 配置的参数
     * @return
     */
    public abstract List<Map<String,Object>> queryJobParamList();

    /**
     * 获取唯一 执行配置
     * @param unique
     * @return
     */
    public Map<String,Object> queryJobParam(String unique){
        return queryJobParamList().get(0);
    }


    public String getName() {
        return AopUtils.getTargetClass(this).getName();
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        //do nothing but logging
        log.info("job to be executed ! jobName:{}",this.getName());
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        //do nothing but logging
        log.info("job execution vetoed ! jobName:{}",this.getName());

    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        //do nothing but logging
        log.info("job was executed ! jobName:{}",this.getName(),jobException);
    }
}
