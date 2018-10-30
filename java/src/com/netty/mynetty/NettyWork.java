package com.netty.mynetty;

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
    public ExecutorService executor;
    protected Selector selector;
    protected AtomicBoolean wakeup = new AtomicBoolean();
    public Queue<Runnable> taskQueue = new ConcurrentLinkedDeque<>();

    public NettyWork(ExecutorService executor){
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
                    wakeup.set(false);
                    selector.select();
                    while(true){
                        final Runnable task = this.taskQueue.poll();
                        if(task == null)
                            break;
                        task.run();
                    }
                    this.process(selector);
                } catch (IOException e) {
                    e.printStackTrace();
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
        for(Iterator<SelectionKey> iterator = selectionKeys.iterator();iterator.hasNext();){
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
            }catch (Exception e){

            }
            if(count < 0 || failure){
                key.cancel();
                System.out.println("客户端断开连接");
            }else{
                System.out.println("收到数据："+ new String(byteBuffer.array()));
                ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
                channel.read(outBuffer);
            }
        }
    }
}