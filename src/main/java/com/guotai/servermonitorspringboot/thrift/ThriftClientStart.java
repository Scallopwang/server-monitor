/*
@File  : ClientStartController.java
@Author: WZC
@Date  : 2021-08-10 9:17
*/
package com.guotai.servermonitorspringboot.thrift;


import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.stereotype.Component;
import thriftmonitor.AgentService;

@Component
public class ThriftClientStart {


    public String clientStart(String ip, String commandStr) {
        try {
            TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 8887), 2000);
            TCompactProtocol tCompactProtocol = new TCompactProtocol(transport);
            AgentService.Client client = new AgentService.Client(tCompactProtocol);
            transport.open();
            String commandRes = client.serverSendCommand(ip, commandStr);
            transport.close();
            return commandRes;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
