package com.bob.jobcenter.service.impl;

import com.bob.jobcenter.jobs.QuartzJobActuator;
import com.bob.jobcenter.jobs.QuartzJobActuatorDisallowConcurrentExecution;
import com.bob.jobcenter.jobs.SyncJob;
import com.bob.jobcenter.model.ScheduleJob;
import com.bob.jobcenter.model.query.ScheduleJobQuery;
import com.bob.jobcenter.service.JobWorkerService;
import com.bob.jobcenter.service.ScheduleJobService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 定时任务执行器
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/6
 */
@Service
public class JobFactoryServiceImpl implements JobWorkerService {
    public static final Logger logger = LoggerFactory.getLogger(JobFactoryServiceImpl.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @PostConstruct
    public void init() throws SchedulerException {
        logger.info("[初始化定时任务工厂]-[开始…………]");
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setId(0L);
        scheduleJob.setName(SyncJob.SYNC_JOB);
        scheduleJob.setGroup(SyncJob.SYNC_JOB);
        scheduleJob.setStatus(ScheduleJob.StatusEnum.RUNNING.getCode());
        scheduleJob.setCron("0 0/1 * * * ? ");
        scheduleJob.setDescription("定时任务中心定时，请勿删除");
        scheduleJob.setBeanClass(SyncJob.class.getName());
        scheduleJob.setIsConcurrent(ScheduleJob.IsConcurrentEnum.CAN.getCode());
        scheduleJob.setBeanName("syncJob");
        scheduleJob.setMethodName("execute");
        scheduleJob.setCreateUser("system");
        scheduleJob.setModifyUser("system");
        // 这里获取任务信息数据
        List<ScheduleJob> jobList = scheduleJobService.queryScheduleJob(new ScheduleJobQuery());
        jobList.add(scheduleJob);
        logger.info("[初始化定时任务工厂]-[添加内部定时任务]-[同步/加载数据库定时任务{}]", scheduleJob);
        for (ScheduleJob job : jobList) {
            addJob(job);
        }
        logger.info("[初始化定时任务工厂]-[结束…………]");
    }


    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    @Override
    public void addJob(ScheduleJob job) throws SchedulerException {
        if (job == null || (ScheduleJob.StatusEnum.RUNNING.getCode() != job.getStatus())) {
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            //创建任务详情
            Class<? extends Job> clazz = (ScheduleJob.IsConcurrentEnum.CAN.getCode() == job.getIsConcurrent()) ? QuartzJobActuator.class : QuartzJobActuatorDisallowConcurrentExecution.class;
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getName(), job.getGroup()).build();
            jobDetail.getJobDataMap().put(ScheduleJob.JOB_DATA_KEY, job);
            //创建任务触发器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getGroup()).withSchedule(scheduleBuilder).build();
            //调度任务
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("[添加定时任务]-[成功]-[{}]", job);
        } else if (!trigger.getCronExpression().equals(job.getCron())) {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            // 按新的cron表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //如果服务器当前时间与你的表达式配置的执行时间差在两小时以内时，动态修改就会出现立即执行的情况。//加上如下代码即可解决
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            logger.info("[添加定时任务]-[更新触发器cron成功]-[{}]", job);
        }
    }


    /**
     * 保证数据库ScheduleJob数据与schedulerFactory中同步
     *
     * @see SyncJob (内部定时任务使用，60s/次)
     */
    public void synJob() throws SchedulerException {
        List<ScheduleJob> jobList = scheduleJobService.queryScheduleJob(new ScheduleJobQuery());
        for (ScheduleJob job : jobList) {
            if (ScheduleJob.StatusEnum.RUNNING.getCode() == job.getStatus()) { //数据库状态是运行
                addJob(job);
            } else {  //数据库状态是停止
                deleteJob(job);
            }
        }
    }


    /**
     * 更改任务状态
     *
     * @param jobId  任务id
     * @param status 状态
     * @throws SchedulerException
     */
    public void changeJobStatus(Long jobId, int status) throws SchedulerException {
        ScheduleJob job = scheduleJobService.getScheduleJobById(jobId);
        if (job == null) {
            return;
        }
        ScheduleJob job4Up = new ScheduleJob();
        job4Up.setId(jobId);
        if (ScheduleJob.StatusEnum.RUNNING.getCode() == status) {
            job4Up.setStatus(status);
        } else if (ScheduleJob.StatusEnum.CLOSE.getCode() == status) {
            deleteJob(job);
            job4Up.setStatus(status);
        } else {
            return;
        }
        scheduleJobService.updateScheduleJob(job);
        logger.info("[更改定时任务状态]-[成功]-[jobId:{},status:{}]", jobId, status);
    }

    /**
     * 更改任务cron表达式
     *
     * @param jobId 任务id
     * @param cron  cron表达式
     * @throws SchedulerException
     */
    public void updateCron(Long jobId, String cron) throws SchedulerException {
        ScheduleJob job = scheduleJobService.getScheduleJobById(jobId);
        if (job == null) {
            return;
        }
        if (ScheduleJob.StatusEnum.RUNNING.getCode() == job.getStatus()) {
            updateJobCron(job);
        }
        ScheduleJob job4Up = new ScheduleJob();
        job4Up.setId(jobId);
        job4Up.setCron(cron);
        scheduleJobService.updateScheduleJob(job);
        logger.info("[更新定时任务cron表达式]-[成功(数据库)]-[jobId:{},cron:{}]", jobId, cron);
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setName(jobKey.getName());
                job.setGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                //TODO job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJob job = new ScheduleJob();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setName(jobKey.getName());
            job.setGroup(jobKey.getGroup());
            job.setDescription("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            //TODO job.setStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCron(cronExpression);
            }
            jobList.add(job);
        }
        return jobList;
    }


    //---------------

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        scheduler.deleteJob(jobKey);
        logger.info("[删除定时任务]-[成功]-[jobId:{},jobKey:{}]", scheduleJob.getId(), jobKey);
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    @Override
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(), scheduleJob.getGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
        logger.info("[更新定时任务cron表达式]-[成功]-[jobId:{},triggerKey:{}]", scheduleJob.getId(), triggerKey);
    }

}
