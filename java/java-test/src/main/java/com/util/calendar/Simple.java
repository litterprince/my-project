package com.util.calendar;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Simple {
    private static Calendar calendar = Calendar.getInstance();

    public static void main(String[] args) {
        Date date = new Date();

        long time = getBeforeDate(date, 0).getTime();
        System.out.println(date.getTime());
        System.out.println(time);
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

    public static Date getBeforeDate(Date date, int beforeDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - beforeDay);
        return calendar.getTime();

    }

    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

}