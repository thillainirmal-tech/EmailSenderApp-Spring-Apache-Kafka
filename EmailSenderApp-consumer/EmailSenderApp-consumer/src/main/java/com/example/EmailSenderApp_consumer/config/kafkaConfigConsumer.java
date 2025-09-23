package com.example.EmailSenderApp_consumer.config;

import com.example.EmailSenderApp_consumer.dto.EmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class kafkaConfigConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(kafkaConfigConsumer.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = "incoming",groupId = "temp-app")
    public void ConsumeData(Object payload) {
        String data=(String) ((ConsumerRecord) payload).value();
        LOGGER.info("Received data from payload: "+payload);
        LOGGER.info("Received data from kafka: "+data);
    }

    @KafkaListener(topics = "outgoing",groupId = "mail-app")
    public void sendEmail(Object payload) throws JsonProcessingException {
        String jsonvalue = (String) ((ConsumerRecord)payload).value();
        EmailRequest emailRequest =mapper.readValue(jsonvalue, EmailRequest.class);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shanmugakannan7549@gmail.com");
        simpleMailMessage.setSubject(emailRequest.getSubject());
        simpleMailMessage.setTo(emailRequest.getToEmail());
        simpleMailMessage.setText(emailRequest.getBody());
        simpleMailMessage.setCc(emailRequest.getCc());
        mailSender.send(simpleMailMessage);
        LOGGER.info("Email sent to: {}",emailRequest.getToEmail());
    }
}
