package com.guotai.servermonitorspringboot.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import thriftmonitor.Agent;

import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface AgentMapper {
    List<Agent> getAgentIPList();

    Agent getAgentByIP(String ip);

    void insertAgent(Agent agent);

    Agent getLatestInfo(String ip);

    List<Timestamp> getAllTime(String ip);

    List<Double> getAllCpuFree(String ip);

    List<Timestamp> getTimeSection(@Param("ip") String ip, @Param("timestamp1") Timestamp timestamp1, @Param("timestamp2") Timestamp timestamp2);

    List<Double> getCpuFreeSection(@Param("ip") String ip, @Param("timestamp1") Timestamp timestamp1, @Param("timestamp2") Timestamp timestamp2);


}
