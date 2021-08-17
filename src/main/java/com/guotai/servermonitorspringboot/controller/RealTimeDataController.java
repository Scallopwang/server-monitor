/*
@File  : RealTimeDataController.java
@Author: WZC
@Date  : 2021-08-09 11:25
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.service.AgentGroupService;
import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.utils.ExecuteCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import thriftmonitor.Agent;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RealTimeDataController {

    private Map<String, String> formMap = new HashMap<String, String>() {{
        put("groupName1", "192.168.15.1");
        put("machineName1", "192.168.15.1");
    }};


    @Autowired
    private LocalAgentService localAgentService;

    @Autowired
    private AgentGroupService agentGroupService;

    @Autowired
    ExecuteCommand executeCommand;

    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        // 获取最新基本信息
        Agent agent = localAgentService.getLatestInfo("192.168.15.1");
        model.addAttribute("cpu_free", agent.cpu_free);
        model.addAttribute("memory_free", agent.memory_free);

        Object machineGroup = agentGroupService.getGroupByIP("192.168.15.1");
        Object machineAlias = agentGroupService.getAliasByIP("192.168.15.1");

        // 设置机器备注名
        String requestMachineName1 = request.getParameter("machineName1");
        if (!machineAlias.equals(requestMachineName1) && requestMachineName1 != null && !requestMachineName1.equals("")) {
            machineAlias = request.getParameter("machineName1");
            agentGroupService.updateAliasByIP(formMap.get("machineName1"), machineAlias.toString());
        }

        // 设置机器组别名
        String requestGroupName1 = request.getParameter("groupName1");
        if (!machineGroup.equals(requestGroupName1) && requestGroupName1 != null && !requestGroupName1.equals("")) {
            machineGroup = request.getParameter("groupName1");
            agentGroupService.updateGroupByIP(formMap.get("groupName1"), machineGroup.toString());
        }

        // 获取、执行指令
        String commandRes = executeCommand.getCommandRes(request.getParameter("commandStr"));
        model.addAttribute("commandRes", commandRes);

        // 获取、搜索设备
        Object searchRes = agentGroupService.getGroupByIP(request.getParameter("searchStr"));
        model.addAttribute("searchRes", searchRes);

        String cpuChooseIp = request.getParameter("cpuChooseIp");
        String cpu_date1 = request.getParameter("cpu_date1");
        String cpu_date2 = request.getParameter("cpu_date2");

        // 画图
        // 获取时间列表 获取cpu使用列表
        if (cpuChooseIp == null || cpuChooseIp.equals("")) {
            List<Timestamp> allTime = localAgentService.getAllTime("192.168.15.1");
            List<Double> allCpuFree = localAgentService.getAllCpuFree("192.168.15.1");
            model.addAttribute("allTime", allTime.toArray());
            model.addAttribute("allCpuFree", allCpuFree);
        } else {
            List<Timestamp> allTime = localAgentService.getAllTime(cpuChooseIp);
            List<Double> allCpuFree = localAgentService.getAllCpuFree(cpuChooseIp);
            model.addAttribute("allTime", allTime.toArray());
            model.addAttribute("allCpuFree", allCpuFree);
        }





        return "index";
    }


}
