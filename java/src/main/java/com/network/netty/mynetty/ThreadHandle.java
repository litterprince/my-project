package com.network.netty.mynetty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadHandle {
    public AtomicInteger bossIndex = new AtomicInteger();
    public static NettyBoss[] bosses;
    public AtomicInteger workIndex = new AtomicInteger();
    public static NettyWork[] workers;

    public ThreadHandle(ExecutorService bossExecutor, ExecutorService workerExecutor){
        this.bosses = new NettyBoss[1];
        for(int i=0;i<bosses.length;i++){
            bosses[i] = new NettyBoss(bossExecutor, this);
        }
        this.workers = new NettyWork[Runtime.getRuntime().availableProcessors() * 2];
        for(int i=0;i<workers.length;i++){
            workers[i] = new NettyWork(workerExecutor);
        }
    }

    public void bind(InetSocketAddress inetSocketAddress){
        try {
            final ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(inetSocketAddress);

            final NettyBoss nextBoss = bosses[Math.abs(bossIndex.getAndIncrement() % bosses.length)];
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        server.register(nextBoss.selector, SelectionKey.OP_ACCEPT);
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    }
                }
            };
            nextBoss.taskQueue.add(runnable);
            if(nextBoss.selector != null){
                if(nextBoss.wakenUp.compareAndSet(false, true)){
                    nextBoss.selector.wakeup();
                }
            }else{
                nextBoss.taskQueue.remove(runnable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
