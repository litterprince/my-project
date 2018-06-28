package thread;

public class TicketRunnable implements Runnable{
    private int ticked = 10;

    public static void main(String[] args) {
        TicketRunnable r1 = new TicketRunnable();
        new Thread(r1, "线程1").start();
        new Thread(r1,"线程2").start();
    }

    @Override
    public void run(){
        for(int i=0;i<10;i++){
            synchronized(this){
                if(this.ticked > 0){
                    try {
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName()+"买票-->"+this.ticked--);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}