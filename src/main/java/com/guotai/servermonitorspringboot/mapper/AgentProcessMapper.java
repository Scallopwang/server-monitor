package com.guotai.servermonitorspringboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import thriftmonitor.AgentProcess;

@Mapper
@Repository
public interface AgentProcessMapper {
    void insertAgentProcess(AgentProcess agentProcess);
}
