package com.guotai.servermonitorspringboot.service;/*
@File  : service.AgentServiceImpl.java
@Author: WZC
@Date  : 2021-08-03 13:41
*/

import com.guotai.servermonitorspringboot.dao.AgentDao;
import com.guotai.servermonitorspringboot.entity.Agent;
import com.guotai.servermonitorspringboot.utils.DataException;
import com.guotai.servermonitorspringboot.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

public class AgentServiceImpl implements AgentService.Iface {
    public Agent agent;
    public SqlSession sqlSession = MyBatisUtils.getSqlSession();

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public Agent sendAgentByIP(String ip, Agent agent) throws DataException, TException {
        System.out.println("server:" + agent.toString());
        setAgent(agent);
        saveData();
        return agent;
    }

    // 持久化到数据库
    public void saveData() {
        AgentDao mapper = sqlSession.getMapper(AgentDao.class);
        mapper.insertAgent(agent);
//        sqlSession.close();
    }
}
