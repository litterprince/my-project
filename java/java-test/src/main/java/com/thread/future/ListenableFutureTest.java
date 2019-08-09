package com.thread.future;

import com.google.common.base.Function;
import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

public class ListenableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(testTransform().get());

        testCallback();
    }

    public static void testCallback(){
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                return "hello";
            }
        });
        // 注册回调
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result: "+ result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public static ListenableFuture<String> testTransform(){
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> listenableFuture = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                return "hello";
            }
        });

        ListenableFuture<String> future = Futures.transform(listenableFuture, new Function<String, String>(){
            @Override
            public String apply(String input) {
                return input + " jeff";
            }
        });
        return future;
    }
}
