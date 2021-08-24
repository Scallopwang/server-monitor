package com.guotai.servermonitorspringboot.service;


import com.guotai.servermonitorspringboot.entity.Email;

public interface EmailService {
    void sendSimpleMail(Email email);
}
