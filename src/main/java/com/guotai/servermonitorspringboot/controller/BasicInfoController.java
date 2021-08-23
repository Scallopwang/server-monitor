/*
@File  : BasicInfoController.java
@Author: WZC
@Date  : 2021-08-23 10:50
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.service.AgentGroupService;
import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.utils.IPMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thriftmonitor.Agent;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BasicInfoController {
    @Autowired
    private IPMap ipMap;
    @Autowired
    private LocalAgentService localAgentService;
    @Autowired
    private AgentGroupService agentGroupService;

    @RequestMapping("/getBasicInfo1")
    @ResponseBody
    public Map<String,Object> getBasicInfo1() {
        // 获取、搜索设备
        Map<String, Object> agent1Map = new HashMap<>();
        Agent agent1 = localAgentService.getLatestInfo(ipMap.getFormMap().get("machineIP1"));
        agent1Map.put("ip", agent1.ip);
        agent1Map.put("cpu_free", String.format("%.2f", agent1.cpu_free*100));
        agent1Map.put("memory_free", agent1.memory_free);

        return agent1Map;
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
}
