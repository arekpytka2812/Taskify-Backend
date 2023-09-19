package com.pytka.taskifybackend.email.service;


import com.pytka.taskifybackend.email.TOs.EmailTO;

public interface EmailProducer {

    void sendEmail(EmailTO email);

}
