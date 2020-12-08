package com.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {

    public static void main(String[] args) {
        /*ForkJoinPool pool = new ForkJoinPool();
        ForkJoin forkJoin = new ForkJoin();
        ForkJoinTask<Integer> future = pool.submit(forkJoin.new forkTask(0, 7));
        pool.shutdown();

        try {
            Integer integer = future.get();
            future.getRawResult();
            System.out.println(integer);
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoin forkJoin = new ForkJoin();
        pool.invoke(forkJoin.new forkTask(0, 7));
        pool.shutdown();
    }

    private class forkTask extends RecursiveTask<Integer> {

        private Integer start;

        private Integer end;

        public forkTask(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 1) {
                if (end == 3) {
                    int i = 1 / 0;
                }
                System.out.println(start + " " + end);
                return end.equals(start) ? end : end + start;
            }

            forkTask leftTask = new forkTask(start, (start + end) / 2);
            forkTask rightTask = new forkTask((start + end) / 2 + 1, end);
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();
        }
    }
}
