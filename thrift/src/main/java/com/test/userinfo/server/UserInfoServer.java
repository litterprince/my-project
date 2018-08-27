package com.test.userinfo.server;

import com.test.userinfo.thrift.UserInfoService;
import com.test.userinfo.thrift.UserInfoServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

public class UserInfoServer {
    public static final int SERVER_PORT = 8090;
    public static final int SERVER_PORT1 = 8091;
    public static final int SERVER_PORT2 = 8092;
    public static final int SERVER_PORT3 = 8093;

    // 简单的单线程服务模型，一般用于测试
    public void startSimleServer() {
        try {
            System.out.println("UserInfoServiceDemo TSimpleServer start ....");
            TProcessor tprocessor = new UserInfoService.Processor<UserInfoService.Iface>(new UserInfoServiceImpl());
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        UserInfoServer server = new UserInfoServer();
        server.startSimleServer();

        //server.startTThreadPoolServer();

        //server.startTNonblockingServer();

        //server.startTHsHaServer();

    }
}
