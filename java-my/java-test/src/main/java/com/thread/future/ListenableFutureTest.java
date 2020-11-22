package com.thread.future;

import java.util.concurrent.*;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;

public class ListenableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(testTransform().get());

        testCallback();
    }

    private static void testCallback() {
        ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(pool);
        ListenableFuture<String> listenableFuture = executorService.submit(() -> "hello");
        // 注册回调
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println("result: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private static ListenableFuture<String> testTransform() {
        ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new ThreadFactoryBuilder().setNameFormat("my-thread-pool-%d").build());
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(pool);
        ListenableFuture<String> listenableFuture = executorService.submit(() -> "hello");

        return Futures.transform(listenableFuture, (Function<String, String>) input -> input + " jeff");
    }
}
