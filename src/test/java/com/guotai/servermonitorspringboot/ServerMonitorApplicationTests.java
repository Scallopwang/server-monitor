package com.guotai.servermonitorspringboot;

import com.guotai.servermonitorspringboot.mapper.AgentMapper;
import com.guotai.servermonitorspringboot.service.LocalAgentService;
import com.guotai.servermonitorspringboot.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thriftmonitor.Agent;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class ServerMonitorApplicationTests {

    @Autowired
    private LocalAgentService localAgentService;

    @Test
    void contextLoads() {
        System.out.println(localAgentService==null);
        Agent agent = new Agent();
        agent.setIp("127.0.0.6");
        agent.setCpu_free(10000);
        agent.setMemory_free(20000);
        localAgentService.insertAgent(agent);
    }

}
