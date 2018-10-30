package com.thread.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

public class ListenableFutureTest {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor( 5, 5, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(poolExecutor);
        MoreExecutors.addDelayedShutdownHook(poolExecutor, 120, TimeUnit.SECONDS);
        //像线程池提交任务，并得到
        ListenableFuture<String> listenableFuture = listeningExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "123";
            }
        }); //可以通过addListener对listenableFuture注册回调，但是通常使用Futures中的工具方法
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result:"+ result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
