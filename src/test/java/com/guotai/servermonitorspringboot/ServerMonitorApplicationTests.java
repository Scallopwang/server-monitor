package com.guotai.servermonitorspringboot;

import com.guotai.servermonitorspringboot.dao.AgentDao;
import com.guotai.servermonitorspringboot.entity.Agent;
import com.guotai.servermonitorspringboot.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerMonitorApplicationTests {

    @Test
    void contextLoads() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        AgentDao mapper = sqlSession.getMapper(AgentDao.class);
        Agent agent = new Agent();
        agent.setIp("127.0.0.5");
        agent.setCpu_free(10000);
        agent.setMemory_free(20000);
        mapper.insertAgent(agent);
//        Agent agentByIP = mapper.getAgentByIP("127.0.0.2");
//        System.out.println(agentByIP.toString());
        sqlSession.close();
    }

}
