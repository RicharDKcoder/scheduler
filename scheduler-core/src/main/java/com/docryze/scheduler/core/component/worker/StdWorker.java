package com.docryze.scheduler.core.component.worker;

import com.docryze.scheduler.core.util.WorkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Component
public class StdWorker {
    @Autowired
    private ExecutorService executorService;

    public <T> Future<T> work(String description,Callable<T> callable){
        return WorkerUtil.work(executorService,callable);
    }
}
