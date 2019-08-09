package com.network.nio.reactor.singleThreadPool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerWithThreadPool implements Runnable {
    private final SocketChannel socket;
    private final SelectionKey key;
    private final ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
    private final ByteBuffer outputBuffer = ByteBuffer.allocate(1024);

    // 初始化一个线程池
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    // 状态码，分别对应读状态，写状态和处理状态
    private static final int READING = 1;
    private static final int SENDING = 2;
    private static final int PROCESSING = 3;
    // 初始的状态码是READING状态，因为Reactor分发任务时新建的Handler肯定是读就绪状态
    private int state = READING;

    public HandlerWithThreadPool(Selector selector, SocketChannel socket) throws IOException {
        this.socket = socket;

        key = socket.register(selector, 0);
        key.attach(this);
        key.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    /**
     * 判断读写数据时候完成的方法
     **/
    private boolean inputIsComplete() {
        return true;
    }

    private boolean outputIsComplete() {
        return true;
    }

    /**
     * 对数据的处理类，比如HTTP服务器就会返回HTTP报文
     **/
    private void process() {
        // 自己实现的服务器功能
    }

    /**
     * 读入数据，确定通道内数据读完以后
     * 状态码要变为 PROCESSING
     * 需要特别注意的是，本方法是在Reactor线程中执行的
     *
     * @throws IOException
     */
    private void read() throws IOException {
        socket.read(inputBuffer);
        if (inputIsComplete()) {
            state = PROCESSING;
            // process
            pool.execute(new Processor());
        }
    }

    private void send() throws IOException {
        socket.write(outputBuffer);
        if (outputIsComplete()) key.cancel();
    }

    /**
     * 这个方法调用了process()方法
     * 而后修改了状态码和兴趣操作集
     * 注意本方法是同步的，因为多线程实际执行的是这个方法
     * 如果不是同步方法，有可能出现
     */
    private synchronized void processAndHandOff() {
        process();
        state = SENDING;
        key.interestOps(SelectionKey.OP_WRITE);
    }

    /**
     * 这个内部类完全是为了使用线程池
     * 这样就可以实现数据的读写在主线程内
     * 而对数据的处理在其他线程中完成
     */
    class Processor implements Runnable {
        public void run() {
            processAndHandOff();
        }
    }

    @Override
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
