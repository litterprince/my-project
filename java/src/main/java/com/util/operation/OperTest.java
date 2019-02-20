package com.util.operation;

/**
 * `byte`：1字节 `short`：2字节 `int`：4字节 `long`：8字节 `float`：4字节 `double`：8字节
 */
public class OperTest {
    public static void main(String[] args){
        test();
    }

    public static void test(){
        int a = 4;
        System.out.println(a >> 1);
        System.out.println(a >> 32+1);

        long b = 4;
        System.out.println(b >> 1);
        System.out.println(b >> 64+1);
    }
}
