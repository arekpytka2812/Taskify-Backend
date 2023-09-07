package com.pytka.taskifybackend.email.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pytka.taskifybackend.email.TOs.EmailTO;
import com.pytka.taskifybackend.email.service.EmailProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:rabbitmq.properties")
public class EmailProducerImpl implements EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void sendEmail(EmailTO email){

        String jsonPayload = null;
        try {
            jsonPayload = objectMapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        rabbitTemplate.convertAndSend("email-queue", jsonPayload);
    }
}
