package com.thread.threadpool.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程池Executor
 * 共享锁CountDownLatch
 * Callable和Future,可以在任务执行完毕之后得到任务的执行结果
 */
public class ConcurrentTaskExecutor {

    private volatile boolean canceled = false;

    private void executeTask() throws Exception {
        int personCount = 10;
        int threadCount = 5;
        CountDownLatch beginLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(personCount);
        List<FutureTask<String>> futureTaskList = new ArrayList<>();
        ExecutorService execService = new ThreadPoolExecutor(threadCount, threadCount, 0, TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(10), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());

        // 初始化行子线程
        for (int i = 0; i < personCount; i++) {
            futureTaskList.add(new FutureTask<>(new ExecuteCallable(beginLatch, endLatch, i, this)));
        }
        for (FutureTask<String> futureTask : futureTaskList) {
            execService.execute(futureTask);
        }
        // new Thread(new InterruptRunnable(this, beginLatch)).start();

        // 被阻塞的子线程开始执行
        beginLatch.countDown();

        // 阻塞主线程，等待所有子线程执行完毕
        endLatch.await();

        // 获取执行结果
        for (FutureTask<String> futureTask : futureTaskList) {
            // 主线程会阻塞，来等待线程返回数据
            System.out.println(futureTask.get());
        }
        execService.shutdown();
    }

    boolean isCanceled() {
        return canceled;
    }

    void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public static void main(String[] args) throws Exception {
        ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor();
        executor.executeTask();
    }
}
