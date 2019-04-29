package cn.miaomiao.kejutiku.utils;

import cn.miaomiao.kejutiku.constant.StringConstant;

import java.util.Collection;

/**
 * 常用判空方法
 *
 * @author miaomiao
 * @date 2019/4/22 15:54
 */
public class VerifyEmptyUtil {

    public static <T> boolean isEmpty(Collection<T> c) {
        return c == null || c.size() <= 0;
    }

    public static <T> boolean isNotEmpty(Collection<T> c) {
        return c != null && c.size() > 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || StringConstant.EMPTY_STR.equals(s);
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static boolean isBlank(String s) {
        return s == null || StringConstant.EMPTY_STR.equals(s) || StringConstant.EMPTY_STR.equals(s.trim());
    }

    public static boolean isNotBlank(String s) {
        return s != null && s.trim().length() > 0;
    }
}
