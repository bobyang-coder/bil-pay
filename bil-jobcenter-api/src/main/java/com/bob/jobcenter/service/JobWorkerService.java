package com.bob.jobcenter.service;

import com.bob.jobcenter.model.ScheduleJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by bob on 2017/10/7.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/7
 */
public interface JobWorkerService {
    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    void addJob(ScheduleJob job) throws SchedulerException;

    /**
     * 保证数据库ScheduleJob数据与schedulerFactory中同步
     */
    void synJob() throws SchedulerException;

    /**
     * 更改任务状态
     *
     * @param jobId  任务id
     * @param status 状态
     * @throws SchedulerException
     */
    void changeJobStatus(Long jobId, int status) throws SchedulerException;

    /**
     * 更改任务 cron表达式
     *
     * @throws SchedulerException
     */
    void updateCron(Long jobId, String cron) throws SchedulerException;


    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    List<ScheduleJob> getAllJob() throws SchedulerException;

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    List<ScheduleJob> getRunningJob() throws SchedulerException;

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException;

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException;

}
