package com.wust.graproject.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName ScheduleConfig
 * @Description TODO
 * @Author leis
 * @Date 2019/2/21 14:08
 * @Version 1.0
 **/
@Configuration
public class ScheduleConfig implements InitializingBean, SchedulingConfigurer {

    private ThreadPoolTaskScheduler executor = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("scheduleTask-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(executor);
    }
}
