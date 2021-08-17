package com.guotai.servermonitorspringboot.thrift;/*
@File  : service.AgentServiceImpl.java
@Author: WZC
@Date  : 2021-08-03 13:41
*/

import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.utils.MyBatisUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import thriftmonitor.Agent;
import thriftmonitor.AgentService;
import thriftmonitor.DataException;


@Service
public class ThriftAgentService implements AgentService.Iface {

    @Autowired
    private LocalAgentService localAgentService;

    @Override
    public Agent sendAgentByIP(String ip, Agent agent) throws DataException, TException {
        System.out.println("server:" + agent.toString());
        try {
            localAgentService.insertAgent(agent);
        } catch (Exception e) {
            e.printStackTrace();
            return agent;
        }
        return agent;
    }

    @Override
    public String getCommand(String ip) throws TException {
        return null;
    }

    @Override
    public String getCollectFre(String ip) throws TException {
        return null;
    }


}
