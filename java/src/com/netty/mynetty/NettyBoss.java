package com.netty.mynetty;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class NettyBoss {
    public ExecutorService executor;
    protected Selector selector;
    protected AtomicBoolean wakenUp = new AtomicBoolean();
    public Queue<Runnable> taskQueue = new ConcurrentLinkedDeque<>();
    protected ThreadHandle threadHandle;

    public NettyBoss(ExecutorService executor, ThreadHandle threadHandle){
        this.threadHandle = threadHandle;
        this.executor = executor;
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> destroy()));

        executor.execute(()->{
            while(!Thread.interrupted()){
                try {
                    wakenUp.set(false);
                    selector.select();
                    while(true){
                        final Runnable task = taskQueue.poll();
                        if(task == null){
                            break;
                        }
                        task.run();
                    }
                    this.process(selector);

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
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
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            NettyWork nextwoker = threadHandle.workers[Math.abs(threadHandle.workIndex.getAndIncrement() % threadHandle.workers.length)];
            Runnable runnable = ()->{
              try {
                  channel.register(nextwoker.selector, SelectionKey.OP_READ);
              } catch (ClosedChannelException e) {
                  e.printStackTrace();
              }
            };
            nextwoker.taskQueue.add(runnable);
            if(nextwoker.selector != null){
                if(nextwoker.wakeup.compareAndSet(false, true)){
                    nextwoker.selector.wakeup();
                }
            }else {
                taskQueue.remove(runnable);
            }
            System.out.println("新客户端连接");
        }
    }
}
