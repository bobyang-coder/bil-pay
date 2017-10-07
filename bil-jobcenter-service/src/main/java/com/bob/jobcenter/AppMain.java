package com.bob.jobcenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bob on 2017/10/6.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/10/6
 */
public class AppMain {
    private static final Logger logger = LoggerFactory.getLogger(AppMain.class);

    private static Boolean running = true;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        context.start();
        try {
            while (running) {
                Thread.sleep(Long.MAX_VALUE);
            }
        } catch (InterruptedException e) {
            logger.error("线程中断异常", e);
        }
    }

    public static void stop() {
        running = false;
    }
}
