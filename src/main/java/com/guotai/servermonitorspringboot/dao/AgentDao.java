package com.guotai.servermonitorspringboot.dao;


import com.guotai.servermonitorspringboot.entity.Agent;


import java.util.List;

public interface AgentDao {
    List<Agent> getAgentIPList();

    Agent getAgentByIP(String ip);

    void insertAgent(Agent agent);
}
