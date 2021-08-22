/*
@File  : AgentProcessService.java
@Author: WZC
@Date  : 2021-08-22 16:38
*/
package com.guotai.servermonitorspringboot.service;

import com.guotai.servermonitorspringboot.mapper.AgentProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thriftmonitor.AgentProcess;


@Service
public class AgentProcessService {
    @Autowired
    AgentProcessMapper agentProcessMapper;

    public void insertAgentProcess(AgentProcess agentProcess) {
       agentProcessMapper.insertAgentProcess(agentProcess);
    }

    public AgentProcess getLatestAgentProcessByIP(String ip) {
        return agentProcessMapper.getLatestAgentProcessByIP(ip);
    }
}
