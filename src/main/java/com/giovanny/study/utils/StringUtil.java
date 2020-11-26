package com.giovanny.study.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @packageName: com.giovanny.study.utils
 * @className: StringUtil
 * @description: //StringUtil
 * @author: YangJun
 * @date: 2020/4/29 9:14
 * @version: v1.0
 **/
public class StringUtil {


    /* *******正则校验******* */
    /**
     * email正则
     */
    public static final String EMAIL_REGEX = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    /**
     * url正则
     */
    public static final String URL_REGEX = "^((https|http)?:\\/\\/)[^\\s]+";
    /**
     * 用户名正则
     */
    public static final String USERNAME_REGEX = "[a-z0-9A-Z]{2,16}";
    /**
     * 密码正则
     */
    public static final String PASSWORD_REGEX = "[a-z0-9A-Z]{6,32}";
    /**
     * mysql通配符 % _
     */
    public static final String MYSQL_REGEX = "[%*_*]";

    public static boolean check(String text, String regex) {
        if (StringUtils.isEmpty(text)) {
            return false;
        } else {
            //使用Pattern.matches和使用Pattern.compile(regex).matcher(input).matches()的效果是一样的，
            // 但是如果多次使用同一个正则可以用后者
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            return matcher.matches();
        }
    }

    /**
     * 转义字符 和 将 \ 转义成 \\\\
     *
     * @param str   将转义的字符串
     * @param regex 正则匹配
     * @return 转义后的字符串
     */
    public static String replacer(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        HashSet<String> hashSet = new HashSet<>();
        str = str.replace("\\", "\\\\\\\\");
        while (matcher.find()) {
            String group1 = matcher.group();
            if (!hashSet.contains(group1)) {
                hashSet.add(group1);
                str = str.replace(group1, "\\" + group1);
            }
        }
        return str;
    }



    /* *****************字符数组组成的所有字符串******************** */

    private static void swap(char[] cs, int index1, int index2) {
        char temp = cs[index1];
        cs[index1] = cs[index2];
        cs[index2] = temp;
    }

    /**
     * 字符数组组成的所有字符串
     *
     * @param cs     字符数组
     * @param start  开始下标（包含）
     * @param length 结束下标（不包含）
     */
    public static void recursionSwap(char[] cs, int start, int length) {
        if (start >= length - 1) {
            print(cs);
            return;
        }
        for (int i = start; i < length; i++) {
            swap(cs, start, i);
            recursionSwap(cs, start + 1, length);
            swap(cs, start, i);
        }
    }

    private static void print(char[] cs) {
        for (char c : cs) {
            System.out.print(c);
        }
        System.out.println();
    }


    /**
     * 通过开始时间和结束时间得到时间间隔内的每一天的日期
     *
     * @param startDate yyyy/MM/dd
     * @param endDate   yyyy/MM/dd
     * @return [yyyy/MM/dd, yyyy/MM/dd]
     */
    public static List<String> getDateStrByTimeInterval(String startDate, String endDate) throws ParseException {
        ArrayList<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plus_1(cal)) {
            list.add(sdf.format(d));
        }
        return list;
    }

    private static long get_D_Plus_1(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }

    /**
     * 最大公约数
     *
     * @param a a
     * @param b b
     * @return 最大公约数
     */
    private static int getGreatestCommonDivisor(int a, int b) {
        return a % b == 0 ? b : getGreatestCommonDivisor(b, a % b);
    }


    /**
     * 递归阶乘
     *
     * @param num 传入值
     * @return 返回阶乘结果
     */
    public static long fac(int num) {
        if (num <= 1) {
            return num;
        } else {
            long facRet = fac(num - 1);
            return facRet * num;
        }
    }

    /**
     * 四舍五入一个数
     *
     * @param before 四舍五入前的值
     * @param scale  保留小数点
     * @return 返回double 四舍五入
     */
    public static double getNumber(double before, int scale) {
        if (scale <= 0) {
            return Math.round(before);
        } else {
            return new BigDecimal(before).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 查找第几个丑数
     * 从1开始的10个丑数分别为1，2，3，4，5，6，8，9，10，12。
     *
     * @param index 第几个
     */
    private static void findUglyNum(int index) {
        int[] num = new int[index];
        int next = 1;
        num[0] = 1;
        int index2 = 0;
        int index3 = 0;
        int index5 = 0;

        while (next < index) {
            int num2 = num[index2] * 2;
            int num3 = num[index3] * 3;
            int num5 = num[index5] * 5;

            num[next] = getSuitable(num2, num3, num5);

            while (num[index2] * 2 <= num[next]) {
                index2++;
            }
            while (num[index3] * 3 <= num[next]) {
                index3++;
            }
            while (num[index5] * 5 <= num[next]) {
                index5++;
            }
            next++;

        }
        System.out.println(num[index - 1]);
    }

    private static int getSuitable(int num2, int num3, int num5) {
        int s = num2;
        if (num3 < s) {
            s = num3;
        }
        if (num5 < s) {
            s = num5;
        }
        return s;
    }


    public static void main(String[] args) {
//        boolean emailCheckRet = check("1102394397@qq.com", EMAIL_REGEX);
//        boolean urlCheckRet = check("http://localhost/8080/haha/ss/2", URL_REGEX);
//        boolean usernameCheckRet = check("qwd", USERNAME_REGEX);
//        boolean passwordCheckRet = check("11111", PASSWORD_REGEX);
//
//        System.out.println(emailCheckRet);
//        System.out.println(urlCheckRet);
//        System.out.println(usernameCheckRet);
//        System.out.println(passwordCheckRet);
//        char[] ch = new char[]{'a', '1', 'b', 'c'};
//        recursionSwap(ch, 1, 3);
//        String qwd = replacer("b_o_o%o\\of\\%", MYSQL_REGEX);
//        long fac = fac(4);
        findUglyNum(6);

    }
}
