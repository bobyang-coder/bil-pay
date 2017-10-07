package com.bob.jobcenter.jobs;

import com.bob.jobcenter.model.ScheduleJob;
import com.bob.jobcenter.utils.ScheduleJobUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度任务执行器（无状态，可并发执行）
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/7
 */
public class QuartzJobActuator implements Job {
    public static final Logger logger = LoggerFactory.getLogger(QuartzJobActuator.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_DATA_KEY);
        ScheduleJobUtil.invokMethod(scheduleJob);
    }
}
