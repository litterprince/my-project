package com.lambdas;

import lombok.Data;
import scala.Int;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTest {
    private static List<Person> list = new ArrayList<>();
    private static Person person = new Person();

    public static void main(String[] args) {
        initParam();
        beanListToIntList();
        beanListToMap();
        testArray();
        listToArray();
    }

    private static void initParam() {
        person.setId(1);
        person.setName("jeff");
        list.add(person);
    }

    private static void beanListToIntList() {
        List<Integer> collect = list.stream().map(Person::getId).collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void beanListToMap() {
        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(Person::getId, Person -> Person));
        System.out.println(map);
    }

    public static void testArray() {
        List<Person> userProductDelayDTOList = new ArrayList<>();
        Person a = new Person();
        a.setId(11);
        a.setStatus(1);
        Person b = new Person();
        b.setId(22);
        b.setStatus(2);
        Person c = new Person();
        c.setId(33);
        c.setStatus(3);
        userProductDelayDTOList.add(a);
        userProductDelayDTOList.add(b);
        userProductDelayDTOList.add(c);
        List<Integer> delayProductIds = userProductDelayDTOList.stream().filter(
                Person-> !(Person.getStatus() == 2 || Person.getStatus() == 3)
        ).map(Person::getId).collect(Collectors.toList());
        System.out.println(delayProductIds);
    }

    public static void listToArray(){
        List<Integer> collect = list.stream().map(Person::getId).collect(Collectors.toList());
        int[] ints = collect.stream().mapToInt(Integer::intValue).toArray();
        for (int i : ints){
            System.out.println(i);
        }
    }

    @Data
    private static class Person {
        private Integer id;
        private String name;
        private Integer status;
    }
}
