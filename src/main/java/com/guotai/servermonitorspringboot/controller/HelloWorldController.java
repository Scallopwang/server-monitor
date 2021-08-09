/*
@File  : HelloWorld.java
@Author: WZC
@Date  : 2021-08-06 15:13
*/
package com.guotai.servermonitorspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    @RequestMapping("/")
    public String getResponse() {
        return "index";
    }
}
