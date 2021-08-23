/*
@File  : ConsoleHeadController.java
@Author: WZC
@Date  : 2021-08-23 10:58
*/
package com.guotai.servermonitorspringboot.controller;

import com.guotai.servermonitorspringboot.entity.ThriftCommunication;
import com.guotai.servermonitorspringboot.service.AgentGroupService;
import com.guotai.servermonitorspringboot.thrift.ThriftClientStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ConsoleHeadController {
    @Autowired
    private ThriftClientStart thriftClientStart;
    @Autowired
    private ThriftCommunication thriftCommunication;
    @Autowired
    private AgentGroupService agentGroupService;

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
}
