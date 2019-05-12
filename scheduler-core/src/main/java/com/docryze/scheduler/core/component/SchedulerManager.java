package com.docryze.scheduler.core.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.function.Function;

@Slf4j
public abstract class SchedulerManager implements ApplicationContextAware {
    Function<ApplicationContext,?> function = (Function<ApplicationContext, Object>) applicationContext -> null;


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        function.apply(applicationContext);
        log.info("scheduler manager function apply !  name:{}",function.getClass().getName());
    }
}
