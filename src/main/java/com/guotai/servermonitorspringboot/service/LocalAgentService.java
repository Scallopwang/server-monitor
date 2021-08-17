/*
@File  : getAgentByIP.java
@Author: WZC
@Date  : 2021-08-10 14:37
*/
package com.guotai.servermonitorspringboot.service;

import com.guotai.servermonitorspringboot.mapper.AgentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thriftmonitor.Agent;

import java.sql.Timestamp;
import java.util.List;

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

    public List<Timestamp> getAllTime(String ip) {
        return agentMapper.getAllTime(ip);
    }

    public List<Double> getAllCpuFree(String ip) {
        return agentMapper.getAllCpuFree(ip);
    }

    public List<Timestamp> getTimeSection(String ip, Timestamp timestamp1, Timestamp timestamp2) {
        return agentMapper.getTimeSection(ip, timestamp1, timestamp2);
    }

    public List<Double> getCpuFreeSection(String ip, Timestamp timestamp1, Timestamp timestamp2) {
        return agentMapper.getCpuFreeSection(ip, timestamp1, timestamp2);
    }


}
