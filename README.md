# scheduler

任务调度层级结构：

    scheduler：
        job： 
            trigger：
            
    scheduler -> job  1 -> n
    job -> trigger    1 -> n
    
    一个scheduler中管理多个job
    一个job可以配置多个trigger


自定义job需要继承 JobContext 抽象类，并且添加 @SchedulerJob 注解 
    
    queryJobParamList：
        查询该job下配置的所有 trigger 配置
    queryJobParam
        抽象类中有默认实现 可以重写覆盖 用于在手动触发任务时 对 trigger 定位
    
    以下三个方法默认实现为 do nothing
        jobToBeExecuted
            当任务被触发时 执行
        jobExecutionVetoed  
            当任务执行异常时 执行
        jobWasExecuted   
            当任务执行完成时 执行
     