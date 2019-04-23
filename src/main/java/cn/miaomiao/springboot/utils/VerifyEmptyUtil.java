package cn.miaomiao.springboot.utils;

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
}
