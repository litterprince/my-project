package com.network.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {

    public static void ioDemo() throws IOException {
        File file = new File("data.txt");
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);
        inputStream.close();
    }

    public static void nioDemo() {
        RandomAccessFile aFile = null;
        try {

            aFile = new RandomAccessFile("src/nio.txt", "rw");
            // channel获取数据
            FileChannel fileChannel = aFile.getChannel();
            // 初始化Buffer，设定Buffer每次可以存储数据量
            // 创建的Buffer是1024byte的，如果实际数据本身就小于1024，那么limit就是实际数据大小
            ByteBuffer buf = ByteBuffer.allocate(1024);
            // channel中的数据写入Buffer
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);

            while (bytesRead != -1) {
                // Buffer切换为读取模式
                buf.flip();
                // 读取数据
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                // 清空Buffer区
                buf.compact();
                // 继续将数据写入缓存区
                bytesRead = fileChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void channelDemo() throws IOException {
        File file = new File("data.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        FileChannel channel = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String data = "java nio";
        buffer.put(data.getBytes());
        buffer.flip();     //此处必须要调用buffer的flip方法
        channel.write(buffer);
        channel.close();
        outputStream.close();
    }
}
