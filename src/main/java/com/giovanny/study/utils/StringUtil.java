package com.giovanny.study.utils;

import org.springframework.util.StringUtils;

import java.util.HashSet;
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
        String qwd = replacer("b_o_o%o\\of\\%", MYSQL_REGEX);
    }
}
