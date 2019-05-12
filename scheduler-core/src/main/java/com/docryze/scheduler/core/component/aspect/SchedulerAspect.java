package com.docryze.scheduler.core.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SchedulerAspect {

    @Pointcut("execution(* com.docryze.scheduler.core.component.JobContext.execute(org.quartz.JobExecutionContext)) && args(context)")
    public void execute(JobExecutionContext context){}

    @Around(value = "execute(context)", argNames = "pjp,context")
    public Object recordTime(ProceedingJoinPoint pjp,JobExecutionContext context) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = pjp.proceed();
        long duration = System.currentTimeMillis() - start;
        String className = pjp.getSignature().getDeclaringTypeName();
        log.info("jobClass: {}, context :{}, record time :{}",className,context,duration);
        return res;
    }


}
