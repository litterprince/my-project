package com.util.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title Test </p>
 * <p> </p>
 * <p>Company: http://www.koolearn.com </p>
 *
 * @author wangzhe01@Koolearn-inc.com
 * @date 2019/12/5 11:25
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(isInteger("323.1"));
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[\\d]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
