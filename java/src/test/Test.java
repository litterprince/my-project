package test;

import javafx.util.Duration;
import sun.misc.BASE64Encoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
       System.out.println(Long.parseLong("-1"));
    }

    public static void time(){
        double t = Duration.valueOf("3ms").toMillis();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(3);
        System.out.println(seconds);
    }

    public static void base64(){
        String value = "demo.test";
        String str = new BASE64Encoder().encode(value.getBytes());
        System.out.println(str);
    }

    public static void date() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date eTime = cal.getTime();
        cal.add(Calendar.HOUR, -4);//仅 startTime 为空才会使用
        Date sTime = sdf.parse("2018-09-27 00:00:00");
        System.out.println("eTime="+eTime);
        System.out.println("sTime="+sTime);
    }

}
