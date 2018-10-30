package com.socket.rpc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static final int LARK_LENGTH_SIZE = 4;
    private static final short CLIENT_RESPONSE_TAG = 1000;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8888);
            //System.out.println("服务端已启动，等待客户端连接..");
            Socket socket=serverSocket.accept();//侦听并接受到此套接字的连接,返回一个Socket对象

            //接收
            InputStream receiver = socket.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            boolean firstLine = true;
            int readLen;
            int totalLen = 0;
            byte[] bs = new byte[1024];
            short recTag = 0;
            int recLength1 = 0;
            while ((readLen = receiver.read(bs)) != -1) {
                bos.write(bs, 0, readLen);
                //判断数据是否读完
                totalLen += readLen;
                if (firstLine) {
                    firstLine = false;
                    recTag = (short) ((bs[0] & 255) << 8 | bs[1] & 255);
                    recLength1 = ((bs[2] & 255) << 24 | (bs[3] & 255) << 16 | (bs[4] & 255) << 8 | bs[5] & 255);
                }
                if (recLength1 + 6 == totalLen)
                    break;
            }
            byte[] buf = bos.toByteArray();
            System.out.println("receive data:"+new String(buf));


            //根据输入输出流和服务端连接
            OutputStream sender = socket.getOutputStream();//获取一个输出流，向服务端发送信息
            byte[] b = getTLVData("hello","jeffService");
            System.out.println("send data:"+ new String(b));
            sender.write(b);
            sender.flush();
            socket.shutdownOutput();//关闭输出流


            //关闭相对应的资源
            sender.close();
            bos.close();
            receiver.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getTLVData(String data, String etcdService) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //TL
            bos.write(writeI16(CLIENT_RESPONSE_TAG));
            final byte[] bytes = data.getBytes("utf-8");
            bos.write(writeI32(LARK_LENGTH_SIZE + bytes.length + LARK_LENGTH_SIZE + etcdService.length()));
            //L1V1
            bos.write(writeI32(bytes.length));
            bos.write(bytes);
            //L2V2
            bos.write(writeI32(etcdService.length()));
            bos.write(etcdService.getBytes("utf-8"));
            //send data value.
            return bos.toByteArray();
        } catch (IOException e) {

        }
        return null;
    }

    private static byte[] writeI16(short i16) {
        byte[] i16out = new byte[2];
        i16out[0] = (byte) (255 & i16 >> 8);
        i16out[1] = (byte) (255 & i16);

        return i16out;
    }

    private static byte[] writeI32(int i32) {
        byte[] i32out = new byte[4];
        i32out[0] = (byte) (255 & i32 >> 24);
        i32out[1] = (byte) (255 & i32 >> 16);
        i32out[2] = (byte) (255 & i32 >> 8);
        i32out[3] = (byte) (255 & i32);
        return i32out;
    }
}
