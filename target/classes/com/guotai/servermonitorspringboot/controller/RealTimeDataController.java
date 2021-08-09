/*
@File  : RealTimeDataController.java
@Author: WZC
@Date  : 2021-08-09 11:25
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.entity.Agent;
import com.guotai.servermonitorspringboot.service.AgentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RealTimeDataController {
    public Agent agent;

    @RequestMapping("/index")
    public String getIndex(Model model) {
        // 如何实时获取来自agent的监控数据，放到前端。
        // 从数据库中读取最新的记录？
        agent = new Agent();
        agent.ip = "0.0.0.0";
        agent.cpu_free = 1.0;
        agent.memory_free = 2.0;
        model.addAttribute("ip", agent.ip);
        model.addAttribute("cpu_free", agent.cpu_free);
        model.addAttribute("memory_free", agent.memory_free);
        return "index";

    }

}
