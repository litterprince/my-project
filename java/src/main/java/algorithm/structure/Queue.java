package algorithm.structure;

public class Queue<T> {
    private Object[] queue;
    private int head = 0;
    private int tail = 0;
    private int capacity = 5;
    private final static int INCREMENT_STEP = 10;

    public Queue() {
        this.queue = new Object[capacity];
    }

    public Queue(int capacity) {
        this.capacity = capacity;
        this.queue = new Object[capacity];
    }

    public void add(T t){
        if(size() + 1 > capacity) {
            expansion();
        }
        queue[tail++] = t;
    }

    public T poll(){
        if(size() < 1 || head > tail){
            return null;
        }
        return (T) queue[head++];
    }

    public int size(){
        return tail - head;
    }

    private void expansion(){
        int size = size();
        capacity += INCREMENT_STEP;
        Object[] array = new Object[capacity];
        System.arraycopy(queue, head, array, head, size);
        queue = array;
    }
}
