package com.docryze.scheduler.core.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WorkerUtil {

    public static  <T> Future<T> work(ExecutorService executorService, Callable<T> callable){
        return executorService.submit(callable);
    }
}
