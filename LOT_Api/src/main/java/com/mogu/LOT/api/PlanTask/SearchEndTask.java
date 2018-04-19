package com.mogu.LOT.api.PlanTask;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


/**
 * Created by chang on 2017/6/27.
 * 超时关闭搜索任务
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SearchEndTask extends QuartzJobBean {
    Logger log = LoggerFactory.getLogger(SearchEndTask.class);

    private static void buildScheduler(Scheduler scheduler,
                                       Class clz,
                                       String jobName,
                                       String jobGroup,
                                       String triggerName,
                                       String triggerGroup,
                                       int millisecond,
                                       Object o) {

        try {
            if (scheduler.checkExists(new JobKey(jobName, jobGroup))) return;
            JobDetail job = JobBuilder.newJob(clz).withIdentity(jobName, jobGroup).build();
            job.getJobDataMap().put("data",o);
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup)
                    .withSchedule(SimpleScheduleBuilder.
                            simpleSchedule().
                            withIntervalInSeconds(millisecond).
                            withRepeatCount(0))
                    .startNow().build();

            scheduler.scheduleJob(job, trigger);

            scheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("*********************************");
        taskTrigger(jobExecutionContext);
    }

    /**
     * 为任务添加监听
     */

    public void taskTrigger(JobExecutionContext jobExecutionContext) {
        log.info("**********任务监听开始*************");

        log.info("**********任务监听结束*************");
    }
}
