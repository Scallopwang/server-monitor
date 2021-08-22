/*
@File  : RealTimeDataController.java
@Author: WZC
@Date  : 2021-08-09 11:25
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.entity.ThriftCommunication;
import com.guotai.servermonitorspringboot.service.AgentGroupService;
import com.guotai.servermonitorspringboot.service.AgentProcessService;
import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.thrift.ThriftClientStart;
import com.guotai.servermonitorspringboot.utils.ExecuteCommand;
import com.guotai.servermonitorspringboot.utils.IPMap;
import com.guotai.servermonitorspringboot.utils.TimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.management.resources.agent;
import thriftmonitor.Agent;
import thriftmonitor.AgentProcess;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RealTimeDataController {
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
    @Autowired
    private IPMap ipMap;
    @Autowired
    private AgentProcessService agentProcessService;

    private Map<String, String> timeChoiceMap = new HashMap<String, String>() {{
        put("day", "%Y-%m-%d 10:00:00");
        put("hour", "%Y-%m-%d %H:00:00");
        put("minute", "%Y-%m-%d %H:%i:00");
    }};

//    private Map<String, String> formMap = new HashMap<String, String>() {{
//        put("machineIP1", agentGroupService.getAllIP().get(0));
//        put("machineIP2", agentGroupService.getAllIP().get(1));
//    }};


    @RequestMapping("/index")
    public String getIndex(Model model, HttpServletRequest request) {
        getIP(model);
        getCpuInfo(model);
        setGroupId(model, request);
        setAlias(model, request);
        exeCommand(model, request);
        searchMachine(model, request);
        getProcess1();
//        getProcess2();
        drawCharts(request);
        return "index";
    }

    @RequestMapping("/getIP")
    @ResponseBody
    public void getIP(Model model) {
        model.addAttribute("machineIP1", ipMap.getFormMap().get("machineIP1"));
        model.addAttribute("machineIP2", ipMap.getFormMap().get("machineIP2"));
        // 获取最新基本信息
//        System.out.println("后端发送ip:"+ipMap.getFormMap().toString());
//        return ipMap.getFormMap();
    }

    @RequestMapping("/cpuInfo")
    @ResponseBody
    public Map<String, String> getCpuInfo(Model model) {
        // 获取最新基本信息
        HashMap<String, String> cpuMap = new HashMap<>();
        Agent agent1 = localAgentService.getLatestInfo(ipMap.getFormMap().get("machineIP1"));
        Agent agent2 = localAgentService.getLatestInfo(ipMap.getFormMap().get("machineIP2"));
        if(agent1 != null) cpuMap.put("cpu1", String.format("%.2f", agent1.cpu_free*100));
        if(agent2 != null) cpuMap.put("cpu2", String.format("%.2f", agent2.cpu_free*100));
        return cpuMap;
    }

    @RequestMapping("/memInfo")
    @ResponseBody
    public Map<String, String> getMemInfo(Model model) {
        // 获取最新基本信息
//        Agent agent = localAgentService.getLatestInfo("192.168.15.1");
//        return String.format("%.2f", agent.memory_free);

        HashMap<String, String> cpuMap = new HashMap<>();
        Agent agent1 = localAgentService.getLatestInfo(ipMap.getFormMap().get("machineIP1"));
        Agent agent2 = localAgentService.getLatestInfo(ipMap.getFormMap().get("machineIP2"));
        if(agent1 != null) cpuMap.put("mem1", String.format("%.2f", agent1.memory_free));
        if(agent2 != null) cpuMap.put("mem2", String.format("%.2f", agent2.memory_free));
        return cpuMap;
    }


    @RequestMapping("/groupId")
    @ResponseBody
    public String setGroupId(Model model, HttpServletRequest request) {
        String machine1msg = "", machine2msg = "";
        // 设置机器组别名
        Object machineGroup1 = agentGroupService.getGroupByIP(ipMap.getFormMap().get("machineIP1"));
        String requestGroupName1 = request.getParameter("groupName1");
        if (!machineGroup1.equals(requestGroupName1) && requestGroupName1 != null && !requestGroupName1.equals("")) {
            machineGroup1 = request.getParameter("groupName1");
            agentGroupService.updateGroupByIP(ipMap.getFormMap().get("machineIP1"), machineGroup1.toString());
            machine1msg = ipMap.getFormMap().get("machineIP1") + " 组别修改成功";
        }

        Object machineGroup2 = agentGroupService.getGroupByIP(ipMap.getFormMap().get("machineIP2"));
        String requestGroupName2 = request.getParameter("groupName2");
        if (!machineGroup2.equals(requestGroupName2) && requestGroupName2 != null && !requestGroupName2.equals("")) {
            machineGroup2 = request.getParameter("groupName2");
            agentGroupService.updateGroupByIP(ipMap.getFormMap().get("machineIP2"), machineGroup2.toString());
            machine2msg = ipMap.getFormMap().get("machineIP2") + " 组别修改成功";
        }

        return machine1msg + machine2msg;
    }

    @RequestMapping("/setAlias")
    @ResponseBody
    public String setAlias(Model model, HttpServletRequest request) {
        String machine1msg = "", machine2msg = "";
        // 设置机器备注名
        Object machineAlias1 = agentGroupService.getAliasByIP(ipMap.getFormMap().get("machineIP1"));
        String requestMachineName1 = request.getParameter("machineName1");
        if (machineAlias1 != null && !machineAlias1.equals(requestMachineName1) && requestMachineName1 != null && !requestMachineName1.equals("")) {
            machineAlias1 = request.getParameter("machineName1");
            agentGroupService.updateAliasByIP(ipMap.getFormMap().get("machineIP1"), machineAlias1.toString());
            machine1msg = ipMap.getFormMap().get("machineIP1") + " 备注修改成功";
        }

        Object machineAlias2 = agentGroupService.getAliasByIP(ipMap.getFormMap().get("machineIP2"));
        String requestMachineName2 = request.getParameter("machineName1");
        if (machineAlias2 != null && !machineAlias2.equals(requestMachineName2) && requestMachineName2 != null && !requestMachineName2.equals("")) {
            machineAlias2 = request.getParameter("machineName1");
            agentGroupService.updateAliasByIP(ipMap.getFormMap().get("machineIP2"), machineAlias2.toString());
            machine2msg = ipMap.getFormMap().get("machineIP2") + " 备注修改成功";
        }
        return machine1msg + machine2msg;
    }

    @RequestMapping("/exeCommand")
    @ResponseBody
    public String exeCommand(Model model, HttpServletRequest request) {
        // 传送指令至agent
        System.out.println("执行接口");
        thriftClientStart.clientStart(request.getParameter("commandStr"));
        // 结果传入前端
        return thriftCommunication.getCommandRes();
    }

    @RequestMapping("/searchMachine")
    @ResponseBody
    public Object searchMachine(Model model, HttpServletRequest request) {
        // 获取、搜索设备
        Object searchRes = agentGroupService.getGroupByIP(request.getParameter("searchStr"));
        model.addAttribute("searchRes", searchRes);
        return searchRes;
    }

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

//    @RequestMapping("/getProcess2")
//    @ResponseBody
//    public Map<String,Object> getProcess2() {
//        // 获取、搜索设备
//        Map<String, Object> process2Map = new HashMap<>();
//        AgentProcess agentProcess2 = agentProcessService.getLatestAgentProcessByIP(ipMap.getFormMap().get("machineIP2"));
//        process2Map.put("ip", agentProcess2.ip);
//        process2Map.put("process_name", agentProcess2.process_name);
//        process2Map.put("process_id", agentProcess2.process_id);
//        process2Map.put("process_mem", agentProcess2.process_mem);
//        process2Map.put("process_start_time", agentProcess2.process_start_time);
//        return process2Map;
//    }



    @RequestMapping("/drawCharts")
    @ResponseBody
    public Map<String, Object> drawCharts(HttpServletRequest request) {
        Map<String, Object> drawMap = new HashMap<>();
        // 获取ip、时间起止点画图
        String cpuChooseIp = request.getParameter("cpuChooseIp");
        String cpu_date1 = request.getParameter("cpu_date1");
        String cpu_date2 = request.getParameter("cpu_date2");
        String timeChoice = request.getParameter("timeChoice");

        if (cpuChooseIp != null && !cpuChooseIp.equals("") && cpu_date1 != null && !cpu_date1.equals("") && cpu_date2 != null && !cpu_date2.equals("")) {
            Timestamp timestamp1 = timeFormat.timeFormat(cpu_date1);
            Timestamp timestamp2 = timeFormat.timeFormat(cpu_date2);
            List<Timestamp> timeSection = localAgentService.getTimeSection(cpuChooseIp, timestamp1, timestamp2, timeChoiceMap.get(timeChoice));
            List<Double> cpuFreeSection = localAgentService.getCpuFreeSection(cpuChooseIp, timestamp1, timestamp2, timeChoiceMap.get(timeChoice));
            System.out.println(timeSection);
            System.out.println(cpuFreeSection);
            drawMap.put("timeSection", timeSection);
            drawMap.put("cpuFreeSection", cpuFreeSection);
            return drawMap;
        } else {
            // 默认使用机器1的IP
            List<Timestamp> timeSection = localAgentService.getLastTimeSection(ipMap.getFormMap().get("machineIP1"));
            List<Double> cpuFreeSection = localAgentService.getLastCpuFreeSection(ipMap.getFormMap().get("machineIP1"));
            drawMap.put("timeSection", timeSection);
            drawMap.put("cpuFreeSection", cpuFreeSection);
            return drawMap;
        }
    }

    @RequestMapping("/memDrawCharts")
    @ResponseBody
    public Map<String, Object> memDrawCharts(HttpServletRequest request) {
        Map<String, Object> drawMap = new HashMap<>();
        // 获取ip、时间起止点画图
        String memChooseIp = request.getParameter("memChooseIp");
        String mem_date1 = request.getParameter("mem_date1");
        String mem_date2 = request.getParameter("mem_date2");
        String memTimeChoice = request.getParameter("memTimeChoice");

        if (memChooseIp != null && !memChooseIp.equals("") && mem_date1 != null && !mem_date1.equals("") && mem_date2 != null && !mem_date2.equals("")) {
            Timestamp timestamp1 = timeFormat.timeFormat(mem_date1);
            Timestamp timestamp2 = timeFormat.timeFormat(mem_date2);
            List<Timestamp> timeSection = localAgentService.getTimeSection(memChooseIp, timestamp1, timestamp2, timeChoiceMap.get(memTimeChoice));
            List<Double> memFreeSection = localAgentService.getMemFreeSection(memChooseIp, timestamp1, timestamp2, timeChoiceMap.get(memTimeChoice));
            drawMap.put("timeSection", timeSection);
            drawMap.put("memFreeSection", memFreeSection);
            return drawMap;
        } else {
            // 默认使用机器1的IP
            List<Timestamp> timeSection = localAgentService.getLastTimeSection(ipMap.getFormMap().get("machineIP1"));
            List<Double> memFreeSection = localAgentService.getLastMemFreeSection(ipMap.getFormMap().get("machineIP1"));
            drawMap.put("timeSection", timeSection);
            drawMap.put("memFreeSection", memFreeSection);
            return drawMap;
        }
    }


}
