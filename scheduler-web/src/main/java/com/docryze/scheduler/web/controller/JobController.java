package com.docryze.scheduler.web.controller;

import com.docryze.scheduler.web.Service.JobService;
import com.docryze.scheduler.web.vo.JobInfo;
import com.docryze.scheduler.web.vo.JobTriggerDetail;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/job")
public class JobController {
    @Autowired
    private JobService jobService;


    @GetMapping("/listJob")
    public List<JobInfo> listJob(){
        return jobService.listJob();
    }

    @GetMapping("/{jobClassName}/listJob")
    public List<JobTriggerDetail> listJobStatus(@PathVariable String jobClassName) throws SchedulerException {
        return jobService.listJobStatus(jobClassName);
    }
}
