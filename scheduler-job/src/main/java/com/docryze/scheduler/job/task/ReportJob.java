package com.docryze.scheduler.job.task;

import com.alibaba.fastjson.JSON;
import com.docryze.scheduler.core.annotation.SchedulerJob;
import com.docryze.scheduler.core.component.JobContext;
import com.docryze.scheduler.job.utils.CronUtil;
import com.docryze.scheduler.job.utils.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SchedulerJob
public class ReportJob extends JobContext {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${scheduler.job.task.report-job.path}")
    private String path;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        //获取待执行的sql
        String reportSql = dataMap.getString(ReportJobParam.STR_SQL.key);

        List<Map<String,Object>> reportResult = jdbcTemplate.queryForList(reportSql);
        //结果是否为null
        boolean resultIsEmpty = CollectionUtils.isEmpty(reportResult);

        String strategy = dataMap.getString(ReportJobParam.STRATEGY.key);

        //如果是监控策略，当结果为空时 不通知
        if(resultIsEmpty && Strategy.MONITOR.key.equals(strategy)){
            //todo 日志记录
            return;
        }

        //如果是报表策略， 当结果为空时 发送文本通知 本次没有结果
        if(resultIsEmpty && Strategy.REPORT.key.equals(strategy)){
            //todo 发送文本通知
            return;
        }


        //获取配置excel的heads
        String heads = dataMap.getString(ReportJobParam.HEADS.key);
        String fileName = dataMap.getString(ReportJobParam.TITLE.key) + System.currentTimeMillis() + ".xlsx";
        String excelFilePath = null;
        try {
            excelFilePath =  generateSqlResExcel(reportResult, heads, fileName);
        } catch (IOException e) {
            //todo 日志记录
            throw new JobExecutionException(e);
        }

        //是否发附件 ？ 为了简单处理， 统一以附件形式发送结果
        String attachment = dataMap.getString(ReportJobParam.ATTACHMENT.key);
        String receiveStr = dataMap.getString(ReportJobParam.RECEIVE_LIST.key);


        //todo 发送邮件



        //todo 更新下次执行时间
        String id = dataMap.getString(ReportJobParam.ID.key);
        String cron = dataMap.getString(ReportJobParam.CRON.key);

        try {
            Date nextDate = CronUtil.nextPointTime(cron);
            String nextDateStr = nextDate.toString();
            String updateNextPointSql = "update t_scheduler_report set next_point = ? where id = ?";
            jdbcTemplate.update(updateNextPointSql,new String[]{nextDateStr,id});
        } catch (ParseException e) {
            //todo 日志记录
            throw new JobExecutionException(e);
        }

        System.out.println(JSON.toJSONString(reportResult));

    }

    private String generateSqlResExcel(List<Map<String, Object>> reportResult, String heads, String fileName) throws IOException {
        return ExcelUtil.generateSqlResExcel(heads == null ? null : heads.split(","),reportResult,path,fileName);
    }

    @Override
    public List<Map<String, Object>> queryJobParamList() {

        String sql = "select '0/10 * * * * ?' as 'cron-key', a.* from `t_scheduler_report` a limit 1;";

        sql = "select id, title, receive_list, '0/10 * * * * ?' as 'cron-key', strategy, attachment, str_sql, heads from t_scheduler_report_trigger where class_name = ? limit 1";


        List<Map<String,Object>> jobParamList = jdbcTemplate.queryForList(sql,this.getClass().getName());

        return jobParamList;
    }

    @Override
    public Map<String, Object> queryJobParam(String unique) {
        String uniqueSql = "select id, title, receive_list, '0/10 * * * * ?' as 'cron-key', strategy, attachment, str_sql, heads from t_scheduler_report_trigger where class_name = ? and id = ?";
        Map<String,Object> paramMap = jdbcTemplate.queryForMap(uniqueSql,this.getClass().getName(),unique);
        return paramMap;
    }


    private enum ReportJobParam{
        ID("id"),
        TITLE("title"),                 //标题
        INTRODUCTION("introduction"),   //描述
        RECEIVE_LIST("receive_list"),   //收件人列表
        CREATOR("creator"),             //创建人
        CRON("cron"),                   //cron表达式
        STRATEGY("strategy"),           //策略 0： 监控  1：报表
        ATTACHMENT("attachment"),       //附件 0：不发附件 1：发附件
        STR_SQL("str_sql"),             //报表sql
        HEADS("heads"),                 //excel表头
        NEXT_POINT("next_point"),       //下次执行时间
        CDATE("cdate"),                 //创建时间
        CLASS_NAME("class_name")        //任务类名
        ;
        private String key;

        ReportJobParam(String key) {
            this.key = key;
        }
    }

    private enum Strategy{
        MONITOR("0"),
        REPORT("1")
        ;

        private String key;

        Strategy(String key) {
            this.key = key;
        }
    }
}
