/*
@File  : RealTimeDataController.java
@Author: WZC
@Date  : 2021-08-09 11:25
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.service.LocalAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import thriftmonitor.Agent;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RealTimeDataController {

    @Autowired
    private LocalAgentService localAgentService;

    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        Agent agent = localAgentService.getLatestInfo("192.168.15.1");
        model.addAttribute("cpu_free", agent.cpu_free);
        model.addAttribute("memory_free", agent.memory_free);
//        String machineName = request.getParameter("machineName");
//        System.out.println(machineName);
        return "index";

    }

    @RequestMapping("/first")
    public String getFirst() {
        return "first";
    }

}