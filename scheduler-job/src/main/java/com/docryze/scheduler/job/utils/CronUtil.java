package com.docryze.scheduler.job.utils;

import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

public class CronUtil {

    /**
     * 获取下一个执行时间
     * @param cron
     * @return
     * @throws ParseException
     */
    public static Date nextPointTime(String cron) throws ParseException {
        CronExpression expression = new CronExpression(cron);
        return expression.getNextValidTimeAfter(new Date());
    }


}
