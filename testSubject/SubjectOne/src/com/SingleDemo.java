package com;

public class SingleDemo {
    private static SingleDemo s = null;

    public SingleDemo(){}

    //同步代码快的demo加锁，安全高效
    public static SingleDemo getInstance(){
        if(s ==null) {
            synchronized (SingleDemo.class) {
                if (s == null)
                    s = new SingleDemo();
            }
        }
        return s;
    }
}
