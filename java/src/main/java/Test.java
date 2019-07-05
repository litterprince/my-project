import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/12/3.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class Test {
    private static Random random = new Random();

    public static void main(String[] args) {
        test();
    }

    public static void testRegEx(String str, String regEx){
        Pattern pat = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matcher = pat.matcher(str);
        boolean flg1 = matcher.matches();
        System.out.println(flg1);
    }

    public static void testSpeed(){
        Test t = new Test();
        Set<Person> persons = new LinkedHashSet<>();
        for (int i = 0; i < 1000000; i++) {
            persons.add(t.new Person(i, "jeff"+i));
        }
        Set<Person> persons2 = new LinkedHashSet<>();
        for (int i = 20; i < 1000000; i++) {
            persons2.add(t.new Person(i, ""));
        }

        // test1
        long start = System.currentTimeMillis();
        final Map<Integer, Person> personMap = Maps.uniqueIndex(persons, new Function<Person, Integer>() {
            @Override
            public Integer apply(Person product) {
                return product.getId();
            }
        });
        for (Person person2 : persons2){
            if(personMap.containsKey(person2.getId())){
                person2.setName(personMap.get(person2.getId()).getName());
            }
        }
        System.out.println("cost = "+ (System.currentTimeMillis() - start));

        // test2
        start = System.currentTimeMillis();
        for(Person person : persons){
            for (Person person2 : persons2){
                if(person2.getId() == person.getId()){
                    person2.setName(person.getName());
                }
            }
        }
        System.out.println("cost = "+ (System.currentTimeMillis() - start));
    }

    public static void test(){
        Test t = new Test();
        Person person = t.new Person();
        int i = 1;
        if(i == 1){
            person.setName(null);
        }
        System.out.println(person.getName());
    }

    class Person {
        private int id;
        private String name;

        Person() {
        }

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}