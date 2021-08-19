/*
@File  : RealTimeDataController.java
@Author: WZC
@Date  : 2021-08-09 11:25
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.entity.ThriftCommunication;
import com.guotai.servermonitorspringboot.service.AgentGroupService;
import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.thrift.ThriftClientStart;
import com.guotai.servermonitorspringboot.utils.ExecuteCommand;
import com.guotai.servermonitorspringboot.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    private TimeFormat timeFormat;

    @Autowired
    private ThriftClientStart thriftClientStart;

    @Autowired
    private ThriftCommunication thriftCommunication;

    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        getCpuInfo(model);
        setGroupId(model, request);
        setAlias(model, request);
        exeCommand(model, request);
        searchMachine(model, request);
        drawCharts(request);
        return "index";
    }

    @RequestMapping("/cpuInfo")
    @ResponseBody
    public String getCpuInfo(Model model) {
        // 获取最新基本信息
        Agent agent = localAgentService.getLatestInfo("192.168.15.1");
//        model.addAttribute("cpu_free", agent.cpu_free);
        return String.format("%.2f", agent.cpu_free * 100);
    }

    @RequestMapping("/memInfo")
    @ResponseBody
    public String getMemInfo(Model model) {
        // 获取最新基本信息
        Agent agent = localAgentService.getLatestInfo("192.168.15.1");
//        model.addAttribute("memory_free", agent.memory_free);
        return String.format("%.2f", agent.memory_free);
    }


    @RequestMapping("/groupId")
    public void setGroupId(Model model, HttpServletRequest request) {
        // 设置机器组别名
        Object machineGroup = agentGroupService.getGroupByIP("192.168.15.1");
        String requestGroupName1 = request.getParameter("groupName1");
        if (!machineGroup.equals(requestGroupName1) && requestGroupName1 != null && !requestGroupName1.equals("")) {
            machineGroup = request.getParameter("groupName1");
            agentGroupService.updateGroupByIP(formMap.get("groupName1"), machineGroup.toString());
        }
    }

    @RequestMapping("/setAlias")
    public void setAlias(Model model, HttpServletRequest request) {
        // 设置机器备注名
        Object machineAlias = agentGroupService.getAliasByIP("192.168.15.1");
        String requestMachineName1 = request.getParameter("machineName1");
        if (!machineAlias.equals(requestMachineName1) && requestMachineName1 != null && !requestMachineName1.equals("")) {
            machineAlias = request.getParameter("machineName1");
            agentGroupService.updateAliasByIP(formMap.get("machineName1"), machineAlias.toString());
        }
    }

    @RequestMapping("/exeCommand")
    public void exeCommand(Model model, HttpServletRequest request) {
        // 传送指令至agent
        thriftClientStart.clientStart(request.getParameter("commandStr"));
        // 结果传入前端
        model.addAttribute("commandRes", thriftCommunication.getCommandRes());
    }

    @RequestMapping("/searchMachine")
    public void searchMachine(Model model, HttpServletRequest request) {
        // 获取、搜索设备
        Object searchRes = agentGroupService.getGroupByIP(request.getParameter("searchStr"));
        model.addAttribute("searchRes", searchRes);
    }

    @RequestMapping("/drawCharts")
    @ResponseBody
    public Map<String, Object> drawCharts(HttpServletRequest request) {
        Map<String, Object> drawMap = new HashMap<>();
        // 获取ip、时间起止点画图
        String cpuChooseIp = request.getParameter("cpuChooseIp");
        String cpu_date1 = request.getParameter("cpu_date1");
        String cpu_date2 = request.getParameter("cpu_date2");
        String timeChoice = request.getParameter("timeChoice");
        System.out.println("timeChoice:" + timeChoice);
        System.out.println("cpuChooseIp:" + cpuChooseIp);
        System.out.println("cpu_date1:" + cpu_date1);
        System.out.println("cpu_date2:" + cpu_date2);
        System.out.println("**********************");

        if (cpuChooseIp != null && !cpuChooseIp.equals("") && cpu_date1 != null && !cpu_date1.equals("") && cpu_date2 != null && !cpu_date2.equals("")) {
            Timestamp timestamp1 = timeFormat.timeFormat(cpu_date1);
            Timestamp timestamp2 = timeFormat.timeFormat(cpu_date2);
            List<Timestamp> timeSection = localAgentService.getTimeSection(cpuChooseIp, timestamp1, timestamp2);
            List<Double> cpuFreeSection = localAgentService.getCpuFreeSection(cpuChooseIp, timestamp1, timestamp2);
            drawMap.put("timeSection", timeSection);
            drawMap.put("cpuFreeSection", cpuFreeSection);
            return drawMap;
        } else {
            List<Timestamp> timeSection = localAgentService.getLastTimeSection("192.168.15.1");
            List<Double> cpuFreeSection = localAgentService.getLastCpuFreeSection("192.168.15.1");
            drawMap.put("timeSection", timeSection);
            drawMap.put("cpuFreeSection", cpuFreeSection);
            return drawMap;
        }
    }


}
