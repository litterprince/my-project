package com.network.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        new SimpleServer(8080);

        System.in.read();
    }

    public SimpleServer(int port) throws IOException {
        final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));

        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            public void completed(AsynchronousSocketChannel result, Void att) {
                // 处理当前连接
                ByteBuffer byteBuffer = ByteBuffer.allocate(32);
                try {
                    byteBuffer.clear();
                    result.read(byteBuffer).get();
                    byteBuffer.flip();
                    System.out.println(Charset.defaultCharset().decode(byteBuffer));

                    // TODO：此处编写业务逻辑

                    //将数据写回客户端
                    result.write(ByteBuffer.wrap("hi client".getBytes()));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    // 接受下一个连接，递归调用
                    listener.accept(null, this);
                }
            }

            public void failed(Throwable exc, Void att) {
                System.out.println("read error" + exc.getMessage());
            }
        });

    }
}
