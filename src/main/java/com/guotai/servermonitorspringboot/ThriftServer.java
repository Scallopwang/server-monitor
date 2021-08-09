/*
@File  : ThriftServerController.java
@Author: WZC
@Date  : 2021-08-09 11:30
*/
package com.guotai.servermonitorspringboot;

import com.guotai.servermonitorspringboot.service.AgentService;
import com.guotai.servermonitorspringboot.service.AgentServiceImpl;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;

public class ThriftServer {
    public static void main(String[] args) {
        System.out.println("server starting...");
        TNonblockingServerSocket tNonblockingServerSocket = null;
        try {
            tNonblockingServerSocket = new TNonblockingServerSocket(8888);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        THsHaServer.Args args1 = new THsHaServer.Args(tNonblockingServerSocket).minWorkerThreads(2).maxWorkerThreads(4);
        AgentService.Processor<AgentServiceImpl> AgentServiceProcessor = new AgentService.Processor<>(new AgentServiceImpl());

        args1.protocolFactory(new TCompactProtocol.Factory());
        args1.transportFactory(new TFramedTransport.Factory());
        args1.processorFactory(new TProcessorFactory(AgentServiceProcessor));
        THsHaServer tHsHaServer = new THsHaServer(args1);
        System.out.println("server started");
        tHsHaServer.serve();
    }
}
