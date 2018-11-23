package test;

import javafx.util.Duration;
import sun.misc.BASE64Encoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        Father son = new Son();
        System.out.println(""+son.getClass());
    }

    static class Father{

    }

    static class Son extends Father{

    }
}
