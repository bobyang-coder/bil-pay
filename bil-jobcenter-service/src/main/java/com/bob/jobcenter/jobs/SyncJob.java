package com.bob.jobcenter.jobs;

import com.bob.jobcenter.service.JobWorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 此定时任务为同步数据所用：保证各个节点中的Factory数据与数据库一致
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/6
 */
@Component
public class SyncJob {
    private static final Logger logger = LoggerFactory.getLogger(SyncJob.class);
    public static final String SYNC_JOB = "SyncJob";
    @Autowired
    private JobWorkerService jobTaskService;

    public void execute() {
        logger.info("[jobCenter内部定时]-[同步数据库数据开始………………]");
        try {
            jobTaskService.synJob();
        } catch (Exception e) {
            logger.error("[同步数据库数据时异常:{}]", e.getMessage(), e);
        }
        logger.info("[jobCenter内部定时]-[同步数据库数据结束………………]");
    }
}
