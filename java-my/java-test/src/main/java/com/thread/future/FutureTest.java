package com.thread.future;

import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class FutureTest {

    private static ExecutorService executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(1), new ThreadFactoryBuilder().setNameFormat("my-trhead-pool-").build());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> future = executor.submit(() -> "hello");
        // future.get会阻塞当前线程直到任务完成
        System.out.println("result = " + future.get());
    }

}
