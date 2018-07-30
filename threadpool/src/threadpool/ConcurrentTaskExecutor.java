package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池Executor
 * 共享锁CountDownLatch
 * 线程间的数据交换Exchanger
 * Callable和Future,可以在任务执行完毕之后得到任务的执行结果
 * */
public class ConcurrentTaskExecutor {
    private volatile boolean canceled = false;

    public void executeTask() throws Exception {
        int personCount = 10;
        int threadCount = 5;

        CountDownLatch beginLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(personCount);
        Exchanger<Integer> exchanger = new Exchanger<Integer>();

        List<FutureTask<String>> futureTaskList = new ArrayList<FutureTask<String>>();
        for (int i = 0; i < personCount; i++) {
            futureTaskList.add(new FutureTask<String>(new ExecuteCallable(beginLatch, endLatch, exchanger, i, this)));
        }

        ExecutorService execService = Executors.newFixedThreadPool(threadCount);
        for (FutureTask<String> futureTask : futureTaskList) {
            execService.execute(futureTask);
        }
        //new Thread(new InterruptRunnable(this, beginLatch)).start();

        beginLatch.countDown();//beginLatch值减一，被阻塞的线程可以继续执行

        Integer totalResult = Integer.valueOf(0);
        for (int i = 0; i < personCount; i++) {
            Integer partialResult = exchanger.exchange(Integer.valueOf(0));//等待exchanger交换后，往下执行
            if(partialResult != 0){
                totalResult = totalResult + partialResult;
                System.out.println(String.format("Progress: %s/%s", totalResult, personCount));
            }
        }

        endLatch.await();//阻塞主线程，等待endLatch值为0再继续执行

        System.out.println("--------------");
        for (FutureTask<String> futureTask : futureTaskList) {
            System.out.println(futureTask.get());//主线程会阻塞，来等待线程返回数据
        }
        execService.shutdown();
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled){
        this.canceled = canceled;
    }
}
