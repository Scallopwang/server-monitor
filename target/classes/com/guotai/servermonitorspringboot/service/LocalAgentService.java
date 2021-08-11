/*
@File  : getAgentByIP.java
@Author: WZC
@Date  : 2021-08-10 14:37
*/
package com.guotai.servermonitorspringboot.service;

import com.guotai.servermonitorspringboot.mapper.AgentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thriftmonitor.Agent;

@Service
public class LocalAgentService {

    @Autowired
    private AgentMapper agentMapper;

    public Agent getAgentByIP(String ip) {
        return agentMapper.getAgentByIP(ip);
    }

    public void insertAgent(Agent agent) {
        agentMapper.insertAgent(agent);
    }

    public Agent getLatestInfo(String ip) {
        return agentMapper.getLatestInfo(ip);
    }

}
