package com.docryze.scheduler.core.exception;

public class SchedulerCoreException extends Exception {


    public SchedulerCoreException(ExceptionCode code){
        super(code.message);
    }



    public enum ExceptionCode{
        // 1 - 100


        // 101 - 200 参数异常
        PARAMS_CLASS_ERROR(101,"非法jobClass表达式"),
        PARAMS_CRON_ERROR(102,"非法cron表达式")


        ;

        int code;
        String message;

        ExceptionCode(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
