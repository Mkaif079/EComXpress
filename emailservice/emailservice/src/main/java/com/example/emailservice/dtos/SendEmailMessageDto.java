package com.example.emailservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendEmailMessageDto {

    private String from;
    private String to;
    private String subject;
    private String body;
}
