package cn.miaomiao.api.utils;

import java.security.SecureRandom;
import java.util.Date;

/**
 * @author miaomiao
 * @date 2019/5/13 15:33
 */
public class RandomUtil {

    private static final SecureRandom BASE_RANDOM = new SecureRandom(new Date().toString().getBytes());

    /**
     * 返回随机秒数，防止redis雪崩
     *
     * @return
     */
    public static Long getRandomSecond(Long timeout) {
        long randomLong;
        if (timeout < 60) {
            randomLong = BASE_RANDOM.nextInt(10) - 5;

        } else if (timeout < 600) {
            randomLong = BASE_RANDOM.nextInt(60) - 30;
        } else {
            randomLong = BASE_RANDOM.nextInt(300) - 150;
        }
        return timeout - randomLong;
    }

    /**
     * 返回1-max间的随机整数
     * @param max 最大值
     * @return int
     */
    public static int getRandom(Integer max){
        return BASE_RANDOM.nextInt(max) + 1;
    }
}
