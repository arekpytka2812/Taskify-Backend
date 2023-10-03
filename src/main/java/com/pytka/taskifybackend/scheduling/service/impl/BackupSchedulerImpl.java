package com.pytka.taskifybackend.scheduling.service.impl;

import com.pytka.taskifybackend.scheduling.service.BackupScheduler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@PropertySource("classpath:backup.properties")
@PropertySource("classpath:scheduling.properties")
public class BackupSchedulerImpl implements BackupScheduler {

    @Value("${backup.database.filepath}")
    private String filepath;

    @Value("${backup.database.filename}")
    private String filename;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${backup.database.triesNumber}")
    private int triesNumber;

    @Override
    @Scheduled(cron = "${scheduling.backup.database.cron}")
    @Async
    public void backupDatabase() {

        int tries = 1;

        int exitCode = doDBBackupOperation();

        while(tries < triesNumber && exitCode != 0){
            exitCode = doDBBackupOperation();
            tries++;
        }

        if(exitCode == 0){
            System.out.println("We did it!");
        }
        else{
            System.out.println("Bruh!");
        }

    }

    @SneakyThrows
    private int doDBBackupOperation(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

        final String containerName = "TASKIFY_DB";
        final String wholeFilepath = filepath + filename + LocalDateTime.now().format(formatter) + ".sql";

        final String command =
                "docker exec " + containerName
                        + " pg_dump --username=" + username
                        + " --password=" + password
                        + " --file=" + wholeFilepath
                        + " " + url;

        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", command);
        processBuilder.redirectErrorStream(true); // Redirect stderr to stdout
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        return process.waitFor();
    }
}
