package com.test.userinfo.client;

import com.test.userinfo.thrift.UserInfoService;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class UserInfoClient {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int SERVER_PORT1 = 8091;
    public static final int SERVER_PORT2 = 8092;
    public static final int SERVER_PORT3 = 8093;
    public static final int TIMEOUT = 30000;

    public void startClient(int userid) {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            UserInfoService.Client client = new UserInfoService.Client(protocol);
            transport.open();
            String result = client.lg_userinfo_getUserNameById(userid);
            System.out.println("Thrify client result =: " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            //处理服务端返回值为null问题
            if (e instanceof TApplicationException
                    && ((TApplicationException) e).getType() ==
                    TApplicationException.MISSING_RESULT) {
                System.out.println("The result of lg_userinfo_getUserNameById function is NULL");
            }
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    public static void main(String[] args) {
        UserInfoClient client = new UserInfoClient();
        client.startClient(1);
        /*client.startClient(2);
        client.startClient(3);*/

        /*client.startClientAsync(1,SERVER_PORT2);

        client.startClientAsync(2,SERVER_PORT3);

        client.startClientAsync(3,SERVER_PORT2);*/

    }
}