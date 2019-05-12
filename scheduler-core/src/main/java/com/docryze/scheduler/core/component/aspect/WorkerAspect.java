package com.docryze.scheduler.core.component.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class WorkerAspect {

    @Pointcut("execution(* com.docryze.scheduler.core.component.worker.StdWorker.work(..)) && args(description,..)")
    public void work(String description){}

    @Around(value = "work(description)", argNames = "pjp,description")
    public Object workTime(ProceedingJoinPoint pjp, String description) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = pjp.proceed();
        long duration = System.currentTimeMillis() - start;
        String className = pjp.getSignature().getDeclaringTypeName();
        log.info("jobClass: {}, description :{}, record time :{}",className,description,duration);
        return res;
    }
}
