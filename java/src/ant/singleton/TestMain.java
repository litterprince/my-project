package ant.singleton;

import java.util.Scanner;

/**
 * 题目一
 提供一个懒汉模式的单实例类实现
 要求：
 1.考虑线程安全。
 2.提供测试代码，测试线程安全性
 * */
public class TestMain {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);

        String num=null;
        System.out.print("请输入创建的线程数量:");
        num=sc.nextLine();
        int count = Integer.valueOf(num);

        Task t = new Task();
        for(int i=0;i<count;i++){
            new Thread(t).start();
        }
        if(t.singles.size() < 2) {
            System.out.println("线程安全测试通过，当前创建的单例对象数量为"+t.singles.size());
        }else{
            System.out.println("线程安全测试不通过，当前创建的单例对象数量为"+t.singles.size());
        }
    }

}
