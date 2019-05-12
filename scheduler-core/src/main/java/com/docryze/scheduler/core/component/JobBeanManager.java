package com.docryze.scheduler.core.component;

import com.docryze.scheduler.core.annotation.SchedulerJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class JobBeanManager extends SchedulerManager {
    private static final List<JobContext> jobClassList = new ArrayList<>();

    private JobBeanManager(){
        this.function = (Function<ApplicationContext, Object>) applicationContext -> {

            String[] names = applicationContext.getBeanNamesForAnnotation(SchedulerJob.class);
            for(String name : names){
                JobContext job = applicationContext.getBean(name,JobContext.class);
                log.info("spring application context manager job bean name:{}",name);
                jobClassList.add(job);
            }
            return null;
        };
    }


    public static List<JobContext> getJobClassList(){
        return jobClassList;
    }
}
