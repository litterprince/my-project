package antexam.threadpool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

public class ExecuteCallable implements Callable<String> {
    private String path;
    private CountDownLatch beginLatch;
    private CountDownLatch endLatch;
    private Exchanger<Integer> exchanger;
    private ConcurrentTaskExecutor concurrentTaskExecutor;
    private Map<String, String> map = new HashMap<>();

    public ExecuteCallable(CountDownLatch beginLatch, CountDownLatch endLatch,
                           Exchanger<Integer> exchanger, String path,
                           ConcurrentTaskExecutor concurrentTaskExecutor) {
        this.beginLatch = beginLatch;
        this.endLatch = endLatch;
        this.exchanger = exchanger;
        this.path = path;
        this.concurrentTaskExecutor = concurrentTaskExecutor;
    }

    @Override
    public String call() throws Exception {
        //beginLatch.await();
        /*if(concurrentTaskExecutor.isCanceled()){
            endLatch.countDown();
            exchanger.exchange(0);
            return String.format("Player :%s is given up", path);
        }*/
        quotaCount(path);
        long millis = (long) (Math.random() * 10 * 1000);
        //String result = String.format("Player :%s arrived, use %s millis", path, millis);
        //Thread.sleep(millis);
        //System.out.println(result);
        exchanger.exchange(1);
        endLatch.countDown();
        return getResult();
    }

    public void quotaCount(String path) {
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] message = line.split(",");
                if(message.length != 3){
                    message = line.split("，");
                }
                String id = message[0];
                String groupId = message[1];
                String quota = message[2];
                if (map.containsKey(groupId)) {
                    String old = map.get(groupId).toString().split(",")[1];
                    if(Float.valueOf(quota) < Float.valueOf(old)){
                        map.put(groupId, id +","+quota);
                    }
                } else {
                    map.put(groupId, id +","+quota);
                }

            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    public String getResult(){
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for(Map.Entry<String, String> entry : entrySet){
            sb.append(entry.getKey()).append(",").append(entry.getValue()).append(",");
        }
        return sb.toString();
    }
}
