/*
@File  : SendEmailController.java
@Author: WZC
@Date  : 2021-08-24 14:58
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.entity.Email;
import com.guotai.servermonitorspringboot.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SendEmailController {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    Email email;

    @RequestMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(HttpServletRequest request) {
        email.setContent(request.getParameter("warningContent"));
        emailService.sendSimpleMail(email);
        return "邮件发送完毕";
    }
}
