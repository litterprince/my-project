package main.java.com.thread.threadpool.complex;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ExecuteCallable implements Callable<String> {
    private int id;
    private CountDownLatch beginLatch;
    private CountDownLatch endLatch;
    private ConcurrentTaskExecutor concurrentTaskExecutor;

    public ExecuteCallable(CountDownLatch beginLatch, CountDownLatch endLatch, int id,
                           ConcurrentTaskExecutor concurrentTaskExecutor) {
        this.beginLatch = beginLatch;
        this.endLatch = endLatch;
        this.id = id;
        this.concurrentTaskExecutor = concurrentTaskExecutor;
    }

    @Override
    public String call() throws Exception {
        beginLatch.await();
        if(concurrentTaskExecutor.isCanceled()){
            endLatch.countDown();
            return String.format("Player :%s is given up", id);
        }
        String result = String.format("Player :%s arrived", id);
        endLatch.countDown();
        return result;
    }
}
