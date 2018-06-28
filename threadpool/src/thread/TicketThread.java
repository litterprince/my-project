package thread;

public class TicketThread extends Thread {
    private int ticket = 10;

    public static void main(String[] args) {
        TicketThread t1 = new TicketThread();
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();
    }

    @Override
    public void run(){
        for(int i=0;i<10;i++){
            synchronized(this){
                if(this.ticket > 0){
                    try{
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName()+"买票-->"+this.ticket--);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
