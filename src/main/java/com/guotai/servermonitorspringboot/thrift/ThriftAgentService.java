package com.guotai.servermonitorspringboot.thrift;/*
@File  : service.AgentServiceImpl.java
@Author: WZC
@Date  : 2021-08-03 13:41
*/

import com.guotai.servermonitorspringboot.entity.ThriftCommunication;
import com.guotai.servermonitorspringboot.service.AgentProcessService;
import com.guotai.servermonitorspringboot.service.LocalAgentService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thriftmonitor.Agent;
import thriftmonitor.AgentProcess;
import thriftmonitor.AgentService;
import thriftmonitor.DataException;


@Service
public class ThriftAgentService implements AgentService.Iface {

    @Autowired
    private LocalAgentService localAgentService;

    @Autowired
    private AgentProcessService agentProcessService;

    @Autowired
    private ThriftCommunication thriftCommunication;

    @Override
    public Agent sendAgentByIP(String ip, Agent agent) throws DataException, TException {
//        System.out.println("server：" + agent.toString());
        try {
            localAgentService.insertAgent(agent);
        } catch (Exception e) {
            e.printStackTrace();
            return agent;
        }
        return agent;
    }

    @Override
    public AgentProcess sendAgentProcessByIP(String s, AgentProcess agentProcess) throws TException {
        System.out.println("server process：" + agentProcess.toString());
        try {
            agentProcessService.insertAgentProcess(agentProcess);
        } catch (Exception e) {
            e.printStackTrace();
            return agentProcess;
        }

        return agentProcess;
    }

    @Override
    public String getCommand(String s, String s1) throws TException {
        return null;
    }

    @Override
    public String getCollectFre(String ip) throws TException {
        return null;
    }

    @Override
    public void sendMsg(String msg) throws TException {
//        System.out.println("接收agent信息为：" + msg);
        thriftCommunication.setCommandRes(msg);
    }


}
