package com.bob.jobcenter.utils;

import com.bob.jobcenter.model.ScheduleJob;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


public class ScheduleJobUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobUtil.class);

    private ScheduleJobUtil() {
    }

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        if (StringUtils.isNotBlank(scheduleJob.getBeanName())) {
            object = SpringBeanFactoryUtil.getBean(scheduleJob.getBeanName());
        }
        if (null == object && StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                object = SpringBeanFactoryUtil.getBean(Class.forName(scheduleJob.getBeanClass()));
            } catch (Exception e) {
                logger.error("[任务名称={}未执行成功，请检查{}是否配置正确!!!]", scheduleJob.getName(), scheduleJob.getBeanClass());
            }
        }
        if (object == null) {
            logger.error("[任务名称={}未执行成功，请检查是否配置正确!!]", scheduleJob.getName());
            return;
        }
        Class clazz = object.getClass();
        try {
            Method method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
            method.invoke(object);
        } catch (SecurityException e) {
            logger.error("[任务名称={}未执行成功，{}!!!]", scheduleJob.getName(), e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            logger.error("[任务名称={}未执行成功，方法名设置错误!!!]", scheduleJob.getName(), e);
        } catch (Exception e) {
            logger.error("[任务名称={}未执行成功，{}!!!]", scheduleJob.getName(), e.getMessage(), e);
        }
        logger.info("[任务名称={}执行成功!!!]", scheduleJob.getName());
    }
}
