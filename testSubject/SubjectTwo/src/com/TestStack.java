package com;

import java.util.Scanner;

/**
 * 题目二：
 设计含最小函数min()、pop()、push()的栈AntMinStack
 要求：
 1.AntMinStack实现测试，满足栈特性
 2.要求min、push、pop、的时间复杂度都是O(1)
 * **/
public class TestStack {
    public static void main(String[] args) {
        AntMinStack stack = new AntMinStack();
        outerloop:
        while (true) {
            // 打印输出等腰三角形
            System.out.println("=======请选择：");
            System.out.println("选择1就进行入栈操作");
            System.out.println("选择2就进行出栈操作");
            System.out.println("选择3就进行最小数输出");
            System.out.println("选择4就输出全部栈元素");
            System.out.println("选择5就退出");
            System.out.println("=======");
            Scanner input = new Scanner(System.in);
            String result = input.next();
            int number = Integer.valueOf(result);
            switch (number) {
                case 1:
                    System.out.println("请输入入栈参数");
                    input = new Scanner(System.in);
                    result = input.next();
                    number = Integer.valueOf(result);
                    stack.push(number);
                    break;
                case 2:
                    stack.pop();
                    break;
                case 3:
                    System.out.println("最小值为:"+stack.min());
                    break;
                case 4:
                    stack.print();
                    break;
                case 5:
                    break outerloop;
                default:
                    break outerloop;
            }
        }
    }
}
