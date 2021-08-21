/*
@File  : getAgentByIP.java
@Author: WZC
@Date  : 2021-08-10 14:37
*/
package com.guotai.servermonitorspringboot.service;

import com.guotai.servermonitorspringboot.mapper.AgentGroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentGroupService {

    @Autowired
    private AgentGroupMapper agentGroupMapper;

    public Object getGroupByIP(String ip) {
        return agentGroupMapper.getGroupByIP(ip);
    }

    public Object getAliasByIP(String ip) {
        return agentGroupMapper.getAliasByIP(ip);
    }

    public void updateAliasByIP(String ip, String alias) {
        agentGroupMapper.updateAliasByIP(ip, alias);
    }

    public void updateGroupByIP(String ip, String groupId) {
        agentGroupMapper.updateGroupByIP(ip, groupId);
    }

    public List<String> getAllIP() {
        return agentGroupMapper.getAllIP();
    }

}
