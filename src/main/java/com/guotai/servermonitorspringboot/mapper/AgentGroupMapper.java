package com.guotai.servermonitorspringboot.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AgentGroupMapper {

    Object getGroupByIP(String ip);

    Object getAliasByIP(String ip);

    void updateAliasByIP(String ip, String alias);

    void updateGroupByIP(String ip, String groupId);


}
