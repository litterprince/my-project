package com.collect.list;

import java.util.ArrayList;
import java.util.List;

public class ListOper {
    public static void main(String[] args) {
        union();

        uniqueUnion();

        intersection();

        diffSet();
    }

    // 并集
    private static void union(){
        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("B");
        list2.add("D");
        list1.addAll(list2);
        System.out.println("并集="+list1);
    }

    // 去重复并集
    private static void uniqueUnion(){
        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("B");
        list2.add("D");
        list2.removeAll(list1);
        list1.addAll(list2);
        System.out.println("去重复并集="+list1);
    }

    // 交集，底层实现本质也是for循环
    private static void intersection(){
        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("B");
        list2.add("D");
        list1.retainAll(list2);
        System.out.println("交集="+list1);
    }

    // 差集
    private static void diffSet(){
        List<String> list1 = new ArrayList<String>();
        list1.add("A");
        list1.add("B");
        list1.add("C");
        List<String> list2 = new ArrayList<String>();
        list2.add("C");
        list2.add("B");
        list2.add("D");
        list1.removeAll(list2);
        list2.removeAll(list1);
        System.out.println("差集="+list1);
    }
}
