package com.docryze.scheduler.core.component.event;

import com.docryze.scheduler.core.listener.DefaultSchedulerListener;
import com.docryze.scheduler.core.listener.DefaultSchedulerListenerBehave;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedulerApplicationEvent implements ApplicationListener {
    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ApplicationReadyEvent){
            try {
                scheduler.getListenerManager()
                        .addSchedulerListener(new DefaultSchedulerListener(new DefaultSchedulerListenerBehave()));
                scheduler.start();
                log.info("spring application Ready ! the scheduler is started");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}
