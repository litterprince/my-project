package com.netty.utils;

public class StringUtils {

    /**
     * 值为null，返回默认值String.
     * @param value 验证值
     * @param defvalue 默认值
     * @return 非null值
     */
    public static String getString(Object value, String defvalue) {
        return null == value ? defvalue : value.toString();
    }

    /**
     * 转为数字int型.
     * @param value 当前值
     * @param defaultValue 默认值
     * @return 返回数字
     */
    public static int getInt(Object value, int defaultValue) {
        if (null == value) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static long getLong(Object value, int defaultValue) {
        if (null == value) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 按指定字符链接成字符串.
     * @param content 数组
     * @param sign 链接字符
     * @return 字符串
     */
    public static String getJoinString(long[] content, String sign) {
        if (null == content || content.length <= 0) {
            return null;
        }

        StringBuilder sbd = new StringBuilder();
        for (int i = 0; i < content.length - 1; i++) {
            sbd.append(content[i]);
            sbd.append(sign);
        }
        sbd.append(content[content.length - 1]);
        return sbd.toString();
    }
}
