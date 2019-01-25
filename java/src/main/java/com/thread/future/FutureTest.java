package main.java.com.thread.future;

import java.util.concurrent.*;

public class FutureTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("result = "+getResult().get());
    }

    public static Future<String> getResult(){
        return executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });
    }
}
