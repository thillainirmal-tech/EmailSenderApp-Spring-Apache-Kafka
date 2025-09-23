package com.example.EmailSenderApp.controller;

import com.example.EmailSenderApp.dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class KafkaController {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/push")
    ResponseEntity<String> pushDataToKafka(@RequestParam String topic, @RequestParam String data) throws ExecutionException, InterruptedException {
        Future future = kafkaTemplate.send(topic,data,data);
        LOGGER.info("Payload pushed to kafka : {}",future.get());
        return ResponseEntity.ok("Data pushed");
    }


    @PostMapping("/asyncmail")
    public ResponseEntity<String> asyncSendEmail(@RequestBody EmailRequest emailRequest) throws ExecutionException, InterruptedException {
       Future future=kafkaTemplate.send("outgoing",emailRequest.getToEmail(),emailRequest);
       LOGGER.info("Email sent successfully"+future.get());
       return ResponseEntity.ok("Email push to kafka successfully");
    }
}
