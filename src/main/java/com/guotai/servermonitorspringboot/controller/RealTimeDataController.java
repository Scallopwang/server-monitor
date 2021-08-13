/*
@File  : RealTimeDataController.java
@Author: WZC
@Date  : 2021-08-09 11:25
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.utils.ExecuteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import thriftmonitor.Agent;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class RealTimeDataController {

    @Autowired
    private LocalAgentService localAgentService;

    @Autowired
    ExecuteCommand executeCommand;

    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        // 获取最新基本信息
        Agent agent = localAgentService.getLatestInfo("192.168.15.1");
        model.addAttribute("cpu_free", agent.cpu_free);
        model.addAttribute("memory_free", agent.memory_free);

        // 执行指令
        String commandRes = executeCommand.getCommandRes(request.getParameter("commandStr"));
        model.addAttribute("commandRes", commandRes);

        // 获取时间列表
        List<Timestamp> allTime = localAgentService.getAllTime("192.168.15.1");
        model.addAttribute("allTime", allTime.toArray());
        System.out.println(allTime.toString());

        // 获取ip列表
        List<Double> allCpuFree = localAgentService.getAllCpuFree("192.168.15.1");

        model.addAttribute("allCpuFree", allCpuFree);

        return "index";

    }

    @RequestMapping("/first")
    public String getFirst() {
        return "first";
    }

}
