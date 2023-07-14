package com.pytka.taskifybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pytka.taskifybackend")
public class TaskifyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskifyBackendApplication.class, args);
    }

}
