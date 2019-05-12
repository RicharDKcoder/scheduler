package com.docryze.scheduler.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WorkThreadConfig {
    @Value("${scheduler.core.config.work-thread-config.nThreads:4}")
    private Integer nThreads;

    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(nThreads);
    }
}
