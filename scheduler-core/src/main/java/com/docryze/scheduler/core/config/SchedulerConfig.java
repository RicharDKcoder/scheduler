package com.docryze.scheduler.core.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SchedulerConfig {
    @Autowired
    private ApplicationContext applicationContext;
    @Value("${scheduler.core.config.scheduler-config.maxThreads:10}")
    private Integer maxThreads;

    @Bean
    public SchedulerFactory schedulerFactory() throws SchedulerException {
        return new StdSchedulerFactory();
//        DirectSchedulerFactory directSchedulerFactory = DirectSchedulerFactory.getInstance();
//        directSchedulerFactory.createScheduler("a",DirectSchedulerFactory.DEFAULT_INSTANCE_ID + "-b",new SimpleThreadPool(10,5),new RAMJobStore());
//        return directSchedulerFactory;
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
