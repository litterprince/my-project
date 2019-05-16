package com.network.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        new SimpleClient("localhost", 8080);

        Thread.sleep(1000);
    }

    public SimpleClient(String host, int port) throws IOException {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        InetSocketAddress serverAddress = new InetSocketAddress(host, port);

        CompletionHandler<Void, ? super Object> handler = new CompletionHandler<Void, Object>() {

            @Override
            public void completed(Void result, Object attachment) {
                client.write(ByteBuffer.wrap("Hello server".getBytes()), null,
                        new CompletionHandler<Integer, Object>() {

                            @Override
                            public void completed(Integer result, Object attachment) {
                                final ByteBuffer buffer = ByteBuffer.allocate(1024);
                                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {

                                    @Override
                                    public void completed(Integer result, ByteBuffer attachment) {
                                        buffer.flip();
                                        System.out.println(new String(buffer.array()));

                                        // TODO: 此处编写业务逻辑

                                        try {
                                            client.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc, ByteBuffer attachment) {
                                        System.out.println("read error" + exc.getMessage());
                                    }

                                });
                            }

                            @Override
                            public void failed(Throwable exc, Object attachment) {
                                System.out.println("write error" + exc.getMessage());
                            }

                        });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("connect error" + exc.getMessage());
            }

        };

        client.connect(serverAddress, null, handler);
    }
}
