package com;

import java.util.HashSet;
import java.util.Set;

public class Task implements Runnable {
    public Set<SingleDemo> singles = new HashSet<SingleDemo>();
    @Override
    public void run() {
        //获取单例
        SingleDemo s = SingleDemo.getInstance();
        //添加单例
        singles.add(s);
    }
}
