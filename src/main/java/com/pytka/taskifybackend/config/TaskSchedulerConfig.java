package com.pytka.taskifybackend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@PropertySource("classpath:scheduling.properties")
public class TaskSchedulerConfig {

    @Value("${scheduling.pool.email}")
    private int emailSchedulerThreadPoolSize;

    @Value("${scheduling.name.email}")
    private String emailSchedulerThreadPoolName;

    @Bean
    @Qualifier("emailTaskScheduler")
    public ThreadPoolTaskScheduler emailTaskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(emailSchedulerThreadPoolSize);
        taskScheduler.setThreadNamePrefix(emailSchedulerThreadPoolName);
        return taskScheduler;
    }
}
