package com.util.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by jm on 2018/11/27.
 * Copyright (c) 2018,jeff.zhew01@gmail.com All Rights Reserved.
 */
public class TantileServer {
    private static final double[] factors = {0.5, 0.7, 0.9, 1.0};
    private static double cnt = 125;

    public static void main(String[] args) {
        caculate();
    }

    public static void caculate(){
        List<TraceStat> stats = new ArrayList<>();
        stats.add(new TraceStat(0.05,25));
        stats.add(new TraceStat(0.15,25));
        stats.add(new TraceStat(0.25,25));
        stats.add(new TraceStat(0.35,25));
        stats.add(new TraceStat(0.45,25));
        Collections.sort(stats);

        long[] ies = new long[factors.length];
        for (int i = 0; i < factors.length; ++i) {
            ies[i] = (long) (cnt * factors[i]);//向下取整
        }

        Tantile[] tantiles = new Tantile[factors.length];
        for (int i = 0, j = 0, cnt = 0; i < stats.size() && j < ies.length; ++i) {
            TraceStat stat = stats.get(i);
            cnt += stat.getTotal();
            while (j < ies.length && cnt >= ies[j]) {
                tantiles[j] = new Tantile(1542269100000L, factors[j], stat.getAvgCost(), "test");
                ++j;
            }
        }

        System.out.println("finish");
    }


    /**
     * 分位值的实体类
     */
    static class Tantile {
        //时间戳
        private long timestamp;
        //分位值
        private double tatile;
        //分位值因子
        private double factor;
        //环境标识
        private String env;

        public Tantile(long timestamp, double factor, double tatile, String env) {
            this.timestamp = timestamp;
            this.tatile = tatile;
            this.factor = factor;
            this.env = env;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public double getTatile() {
            return tatile;
        }

        public void setTatile(double tatile) {
            this.tatile = tatile;
        }

        public double getFactor() {
            return factor;
        }

        public void setFactor(double factor) {
            this.factor = factor;
        }


        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }

        @Override
        public String toString() {
            return "timestamp: " + timestamp + "  tatile: " + tatile + " factor: " + factor + " env : " + env;
        }
    }


    /**
     * 网络请求调用统计的实体类
     */
    static class TraceStat implements Comparable<TraceStat> {
        //平均耗时
        private double avgCost;
        //总请求数
        private long total;

        public TraceStat(double avgCost, long total){
            this.avgCost=avgCost;
            this.total=total;
        }
        public double getAvgCost() {
            return avgCost;
        }

        public void setAvgCost(double avgCost) {
            this.avgCost = avgCost;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }


        @Override
        public int compareTo(TraceStat o) {
            return Double.compare(this.avgCost,o.getAvgCost());
        }
    }
}
