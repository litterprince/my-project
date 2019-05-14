package com.network.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleClient {
    private AsynchronousSocketChannel channel;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        SimpleClient client = new SimpleClient("localhost", 7788);
        client.write((byte) 11);
    }

    public SimpleClient(String host, int port) throws IOException, InterruptedException, ExecutionException {
        this.channel = AsynchronousSocketChannel.open();
        Future<?> future = channel.connect(new InetSocketAddress(host, port));
        future.get();
    }

    public void write(byte b) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put(b);
        byteBuffer.flip();
        channel.write(byteBuffer);
    }
}
