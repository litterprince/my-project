package exam.minstack;

public class AntMinStack {
    private int[] data = null;
    private int[] min = null;
    private int maxSize=0;   //栈容量
    private int top =0;  //栈顶指针

    /**
     * 构造函数：根据给定的size初始化栈
     */
    AntMinStack(){
        this(10);   //默认栈大小为10
    }

    AntMinStack(int initialSize){
        if(initialSize >=0){
            this.maxSize = initialSize;
            data = new int[initialSize];
            min = new int[initialSize];
            top = 0;
        }else{
            throw new RuntimeException("初始化大小不能小于0：" + initialSize);
        }
    }

    //进栈,第一个元素top=0；
    public boolean push(int e){
        if(top == maxSize){
            throw new RuntimeException("栈已满，无法将元素入栈！");
        }else{
            data[top]=e;
            if(top == 0 || e < min[top-1]){
                min[top] = e;
            }else{
                min[top] = min[top-1];
            }
            top++;
            return true;
        }
    }

    //弹出栈顶元素
    public int pop(){
        if(top == -1){
            throw new RuntimeException("栈为空！");
        }else{
            return (int)data[top--];
        }
    }

    public int min(){
        return min[top-1];
    }

    //判空
    public boolean empty(){
        return top==-1 ? true : false;
    }

    //查看栈顶元素但不移除
    public int peek(){
        if(top == -1){
            throw new RuntimeException("栈为空！");
        }else{
            return (int)data[top];
        }
    }

    //返回对象在堆栈中的位置，以 1 为基数
    public int search(int e){
        int i=top;
        while(top != -1){
            if(peek() != e){
                top --;
            }else{
                break;
            }
        }
        int result = top+1;
        top = i;
        return result;
    }

    public void print(){
        if(top > 0) {
            System.out.print("全部元素:");
            for (int i = 0; i < top; i++) {
                System.out.print(data[i]+" ");
            }
        }
    }
}
