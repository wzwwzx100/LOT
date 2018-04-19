package com.mogu.LOT.api.PlanTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chang on 2017/7/5.
 * 任务超时
 * 1. 自动结束任务
 * 2. 用户积分计算
 */
public class TaskTimeoutEndJob implements Job {
    Logger logger = LoggerFactory.getLogger(TaskTimeoutEndJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.error("任务超时………………");
    }
}
