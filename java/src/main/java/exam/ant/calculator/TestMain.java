package exam.ant.calculator;

import java.util.Scanner;

/**
 * 题目四：
 设计数据结构与算法，计算算数表达式，需要支持基本计算，加减乘除，满足计算优先级例如输入 3*0+3+8+9*1 输出20。括号，支持括号，例如输入 3+（3-0）*2 输出 9假设所有的数字均为整数，无需考虑精度问题。
 要求：
 1.代码结构清晰
 2.数据结构选型合理。
 * */
public class TestMain {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        //获取用户输入的字符串
        String expression=null;
        System.out.print("请输入表达式:");
        expression=sc.nextLine();

        System.out.println("你输入的字符为:"+expression);
         //"(0*1--3)-5/-4-(3*(-2.13))";
        double result = Calculator.conversion(expression);
        System.out.println("计算结果为:"+expression + " = " + result);
    }
}
