package com.network.nio.reactor.multi;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyBoss {
    private ExecutorService executor;
    protected Selector selector;
    AtomicBoolean wakenUp = new AtomicBoolean();// 用于唤醒阻塞线程，使selector立即返回
    Queue<Runnable> taskQueue = new ConcurrentLinkedDeque<>();
    private ThreadHandle threadHandle;

    NettyBoss(ExecutorService executor, ThreadHandle threadHandle){
        this.threadHandle = threadHandle;
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
                while(!Thread.interrupted()){
                    try {
                        wakenUp.set(false);
                        selector.select();
                        while(!Thread.interrupted()){
                            final Runnable task = taskQueue.poll();
                            if(task == null){
                                break;
                            }
                            task.run();
                            Thread.sleep(100);
                        }
                        process(selector);

                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void destroy() {
        System.out.println("boss executor shutdown...");
        executor.shutdown();
    }

    private void process(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        if(selectionKeys.isEmpty()){
            return;
        }
        for(Iterator<SelectionKey> i = selectionKeys.iterator();i.hasNext();){
            SelectionKey key = i.next();
            i.remove();
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            final SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            final NettyWork nextWorker = threadHandle.workers[Math.abs(threadHandle.workIndex.getAndIncrement() % threadHandle.workers.length)];
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        channel.register(nextWorker.selector, SelectionKey.OP_READ);
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    }
                }
            };
            nextWorker.taskQueue.add(runnable);
            if(nextWorker.selector != null){
                if(nextWorker.wakeup.compareAndSet(false, true)){
                    nextWorker.selector.wakeup();
                }
            }else {
                taskQueue.remove(runnable);
            }
            System.out.println("新客户端连接");
        }
    }
}
