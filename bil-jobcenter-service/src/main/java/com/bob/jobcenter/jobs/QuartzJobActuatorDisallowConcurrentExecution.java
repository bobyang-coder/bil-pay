package com.bob.jobcenter.jobs;

import com.bob.jobcenter.model.ScheduleJob;
import com.bob.jobcenter.utils.ScheduleJobUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 调度任务执行器（有状态，不可以并发执行）
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/7
 */
@DisallowConcurrentExecution
public class QuartzJobActuatorDisallowConcurrentExecution implements Job {
    public static final Logger log = LoggerFactory.getLogger(QuartzJobActuatorDisallowConcurrentExecution.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_DATA_KEY);
        ScheduleJobUtil.invokMethod(scheduleJob);

    }
}