package com.pytka.taskifybackend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EMAIL_QUEUE = "email-queue";

    @Bean
    public Queue queue(){
        return new Queue(EMAIL_QUEUE);
    }

}
