import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigDecimal.ROUND_UP;

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

    public static void testTime() {
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.now().minusDays(1);
        // 间隔（只试过2的倍数）
        int interval = 4;
        Stream.iterate(0, item -> item + interval).limit(24 / interval).map(
                startHour -> {
                    LocalTime startLocalTime = LocalTime.of(startHour, 0, 0);
                    LocalTime endLocalTime = LocalTime.of(startHour + interval - 1, 59, 59);
                    LocalDateTime startTime = LocalDateTime.of(localDate, startLocalTime);
                    LocalDateTime endTime = LocalDateTime.of(localDate, endLocalTime);
                    System.out.println(startTime.format(fmt1) + "-" + endTime.format(fmt1));
                    return null;
                }
        ).collect(Collectors.toList());
    }

    public static void testDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        List<LocalDateTime> dates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());
        System.out.println("param");
        for (int i = 0; i < dates.size() - 1; i++) {
            System.out.println(dateTimeFormatter.format(dates.get(i)) + "," + dateTimeFormatter.format(dates.get(i + 1)));
        }
    }

    public static void testRegEx(String str, String regEx) {
        Pattern pat = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matcher = pat.matcher(str);
        boolean flg1 = matcher.matches();
        System.out.println(flg1);
    }

    public static void testSpeed() {
        Test t = new Test();
        Set<Person> persons = new LinkedHashSet<>();
        for (int i = 0; i < 1000000; i++) {
            persons.add(t.new Person(i, "jeff" + i));
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
        for (Person person2 : persons2) {
            if (personMap.containsKey(person2.getId())) {
                person2.setName(personMap.get(person2.getId()).getName());
            }
        }
        System.out.println("cost = " + (System.currentTimeMillis() - start));

        // test2
        start = System.currentTimeMillis();
        for (Person person : persons) {
            for (Person person2 : persons2) {
                if (person2.getId() == person.getId()) {
                    person2.setName(person.getName());
                }
            }
        }
        System.out.println("cost = " + (System.currentTimeMillis() - start));
    }

    public static void testMultimap() {
        Test t = new Test();
        Person person = t.new Person();
        person.setName("jeff");
        Person person1 = t.new Person();
        person1.setName("jeff1");
        Multimap<String, Person> orderProductMultimap = ArrayListMultimap.create();
        orderProductMultimap.put(person.getName(), person);
        orderProductMultimap.put(person.getName(), person1);
        Collection<Person> ordersProductMultiList = orderProductMultimap.get("jeff");
        ArrayList<Person> orderProductList = new ArrayList<>(ordersProductMultiList);
        System.out.println(orderProductList.get(1).getName());
    }

    public static void testBigDecimal() {
        BigDecimal a = new BigDecimal(2.22);
        BigDecimal b = new BigDecimal("2.22");
        System.out.println("a 校验前：" + a);
        if (!a.setScale(2, BigDecimal.ROUND_DOWN).equals(a)) {
            System.out.println("a 校验错误");
        }
        System.out.println("a 校验后：" + a);
        if (!b.setScale(2, BigDecimal.ROUND_DOWN).equals(b)) {
            System.out.println("b 校验错误");
        }
    }

    public static void test() {
        List<Integer> excelDataList = new ArrayList<>();
        excelDataList.add(1);//0
        excelDataList.add(2);//1
        excelDataList.add(3);//2
        excelDataList.add(4);//3
        excelDataList.add(5);//4
        int fromIndex = 0;
        // 总页数
        int PAGE_SIZE = 2;
        int totalSize = excelDataList.size();
        int pageSize = (new BigDecimal(totalSize).divide(new BigDecimal(PAGE_SIZE), ROUND_UP)).intValue();
        for (int pageNo = 0; pageNo < pageSize; pageNo++) {
            int toIndex = fromIndex + PAGE_SIZE;
            if (toIndex > totalSize) {
                toIndex = totalSize;
            }

            System.out.println(Arrays.toString(excelDataList.subList(fromIndex, toIndex).toArray()));
            fromIndex += PAGE_SIZE;
        }
    }

    class Person {
        private int id;
        private String name;

        Person() {
        }

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        int getId() {
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