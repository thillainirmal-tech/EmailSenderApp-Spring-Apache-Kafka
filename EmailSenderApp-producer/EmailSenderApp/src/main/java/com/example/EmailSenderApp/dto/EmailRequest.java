package com.example.EmailSenderApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String toEmail;
    private String subject;
    private String body;
    private String cc;
}
