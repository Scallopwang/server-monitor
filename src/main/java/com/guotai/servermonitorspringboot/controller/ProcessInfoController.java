/*
@File  : ProcessInfoController.java
@Author: WZC
@Date  : 2021-08-23 10:52
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.service.AgentProcessService;
import com.guotai.servermonitorspringboot.utils.IPMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thriftmonitor.AgentProcess;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProcessInfoController {
    @Autowired
    private IPMap ipMap;
    @Autowired
    private AgentProcessService agentProcessService;

    @RequestMapping("/getProcess1")
    @ResponseBody
    public Map<String,Object> getProcess1() {
        // 获取、搜索设备
        Map<String, Object> process1Map = new HashMap<>();
        AgentProcess agentProcess1 = agentProcessService.getLatestAgentProcessByIP(ipMap.getFormMap().get("machineIP1"));
        process1Map.put("ip", agentProcess1.ip);
        process1Map.put("process_name", agentProcess1.process_name);
        process1Map.put("process_id", agentProcess1.process_id);
        process1Map.put("process_mem", agentProcess1.process_mem);
        process1Map.put("process_start_time", agentProcess1.process_start_time);
        return process1Map;
    }
}
