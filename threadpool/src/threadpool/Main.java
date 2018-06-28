package threadpool;

public class Main {
    public static void main(String[] args) throws Exception {
        ConcurrentTaskExecutor executor = new ConcurrentTaskExecutor();
        executor.executeTask();
    }
}