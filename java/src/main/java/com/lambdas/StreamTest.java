package com.lambdas;

import lombok.Data;

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
    }

    private static void initParam(){
        person.setId(1);
        person.setName("jeff");
        list.add(person);
    }

    private static void beanListToIntList(){
        List<Integer> collect = list.stream().map(Person::getId).collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void beanListToMap(){
        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(Person::getId, Person -> Person));
        System.out.println(map);
    }

    @Data
    private static class Person{
        private Integer id;
        private String name;
    }
}
