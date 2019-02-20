package com.util.calendar;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Simple {
    private static Calendar calendar = Calendar.getInstance();

    public static void main(String[] args) {
        getTime("");
    }

    public static String getESIndex(String index, Integer cycle) {
        Date date = new Date();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        cycle = calendar.get(cycle);
        int year = calendar.get(Calendar.YEAR);
        return index + "_" + year + "_" + cycle;//trace_stats_2018_46
    }

    public static void getTime(String time){
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isNotBlank(time)) {
            try {
                cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date startTime = cal.getTime();
        System.out.println("startTime="+startTime);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = cal.getTime();
        System.out.println("endTime="+endTime);
    }

}