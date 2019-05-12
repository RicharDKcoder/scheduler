package com.docryze.scheduler.core.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SchedulerConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactory schedulerFactory(){
        return new StdSchedulerFactory();
    }

    @Bean(destroyMethod = "shutdown")
    public Scheduler scheduler(SchedulerFactory schedulerFactory) throws SchedulerException {

        Scheduler s = schedulerFactory.getScheduler();
        s.setJobFactory((bundle, scheduler) -> applicationContext.getBean(bundle.getJobDetail().getJobClass()));
        log.info("scheduler and jobFactory config succeed ！");
        return s;
    }

    @Bean
    public ListenerManager listenerManager(Scheduler scheduler) throws SchedulerException {
        return scheduler.getListenerManager();
    }
}
