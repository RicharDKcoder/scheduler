package com.docryze.scheduler.core.web;


import com.docryze.scheduler.core.bean.JobStatus;
import com.docryze.scheduler.core.component.monitor.JobMonitor;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/core/monitor")
public class MonitorController {
    @Autowired
    private JobMonitor jobMonitor;



    @GetMapping("/listAllJobStatus")
    public List<JobStatus> listAllJobStatus(){
        List<JobStatus> jobStatusList = new ArrayList<>();
        try {
            jobStatusList = jobMonitor.listJobStatus(null);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return jobStatusList;
    }

    @GetMapping("/{groupName}/listJobStatus")
    public List<JobStatus> listJobStatusWithGroup(@PathVariable String groupName){
        List<JobStatus> jobStatusList = new ArrayList<>();
        try {
            jobStatusList = jobMonitor.listJobStatus(groupName);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return jobStatusList;
    }


    @GetMapping("/executeNow/{className}/{id}")
    public void executeNow(@PathVariable String className, @PathVariable String id){
        try {
            jobMonitor.executeNow(className,id);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
