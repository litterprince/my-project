package algorithm.structure;

public class Stack<T> {
    private Object[] stack;
    private int top = 0;
    private int capacity = 5;
    private final static int INCREMENT_STEP = 10;

    public Stack(){
        this.stack = new Object[capacity];
    }

    public Stack(int capacity){
        this.capacity = capacity;
        this.stack = new Object[capacity];
    }

    public void push(T t){
        if(size() + 1 > capacity){
            expansion();
        }
        stack[top++] = t;
    }

    public T pop(){
        if(top < 1){
            return null;
        }
        return (T) stack[--top];
    }

    public int size(){
        return top;
    }

    private void expansion(){
        capacity += INCREMENT_STEP;
        Object[] array = new Object[capacity];
        System.arraycopy(stack, 0, array, 0, top);
        stack = array;
    }
}
