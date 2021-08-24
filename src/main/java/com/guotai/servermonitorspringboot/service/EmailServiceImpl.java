/*
@File  : EmailServiceImpl.java
@Author: WZC
@Date  : 2021-08-24 15:12
*/
package com.guotai.servermonitorspringboot.service;

import com.guotai.servermonitorspringboot.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        message.setTo(email.getReceiver());
        javaMailSender.send(message);
    }
}
