package com.guotai.servermonitorspringboot;

import com.guotai.servermonitorspringboot.service.AgentGroupService;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

//@SpringBootTest
class ServerMonitorApplicationTests {

//    @Autowired
//    private AgentGroupService agentGroupService;

    @Test
    void contextLoads() {
        AgentGroupService agentGroupService = new AgentGroupService();
        agentGroupService.updateGroupByIP("192.168.15.1", "0321");
    }

    @Test
    void myTest() {
        try{
            String commandBaseStr = "cmd /C ";
            String commandStr = "dir";
            Process process=Runtime.getRuntime().exec(commandBaseStr+commandStr);
            InputStreamReader reader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            LineNumberReader line = new LineNumberReader(reader);
            StringBuilder resStr = new StringBuilder();
            String str;
            while((str=line.readLine())!=null){
                resStr.append(str).append("\n");
            }
            System.out.println(resStr);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void test() {
        System.out.println(System.getProperty("os.name"));
    }

}
