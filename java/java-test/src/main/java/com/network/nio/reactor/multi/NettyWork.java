package com.network.nio.reactor.multi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyWork {
    private ExecutorService executor;
    protected Selector selector;
    AtomicBoolean wakeup = new AtomicBoolean();// 用于唤醒阻塞线程，使selector立即返回
    Queue<Runnable> taskQueue = new ConcurrentLinkedDeque<>();

    NettyWork(ExecutorService executor) {
        this.executor = executor;
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                destroy();
            }
        }));

        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        wakeup.set(false);
                        selector.select();
                        // 等待boss唤醒，task执行绑定channel的read任务
                        while (!Thread.interrupted()) {
                            final Runnable task = taskQueue.poll();
                            if (task == null)
                                break;
                            task.run();
                            Thread.sleep(100);
                        }
                        process(selector);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void destroy() {
        System.out.println("worker executor shutdown...");
        executor.shutdown();
    }

    private void process(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        if (selectionKeys.isEmpty()) {
            return;
        }
        for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
            SelectionKey key = iterator.next();
            iterator.remove();
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            int count = 0;
            boolean failure = true;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                count = channel.write(byteBuffer);
                failure = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (count < 0 || failure) {
                key.cancel();
                System.out.println("客户端断开连接");
            } else {
                System.out.println("收到数据：" + new String(byteBuffer.array()));
                ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
                channel.read(outBuffer);
            }
        }
    }
}
