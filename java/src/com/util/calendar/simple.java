package com.util.calendar;

import java.util.Calendar;
import java.util.Date;

public class simple {
    private static Calendar calendar = Calendar.getInstance();

    public static void main(String[] args) {
        System.out.println(1000*(System.currentTimeMillis()/1000));
        System.out.println(System.currentTimeMillis());
    }

    private static String getESIndex(String index, Integer cycle) {
        Date date = new Date();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        cycle = calendar.get(cycle);
        int year = calendar.get(Calendar.YEAR);
        return index + "_" + year + "_" + cycle;//trace_stats_2018_46
    }
}
