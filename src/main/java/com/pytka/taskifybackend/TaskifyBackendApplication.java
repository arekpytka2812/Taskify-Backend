package com.pytka.taskifybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.pytka.taskifybackend")
@EnableAsync
@EnableScheduling
public class TaskifyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskifyBackendApplication.class, args);
    }

}
