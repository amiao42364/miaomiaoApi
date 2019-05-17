package cn.miaomiao.api.utils;

import java.security.SecureRandom;

/**
 * @author miaomiao
 * @date 2019/5/13 15:33
 */
public class RandomUtil {

    /**
     * 返回随机秒数，防止redis雪崩
     *
     * @return
     */
    public static Long getRandomSecond(Long timeout) {
        SecureRandom random = new SecureRandom(timeout.toString().getBytes());
        long randomLong;
        if (timeout < 60) {
            randomLong = random.nextInt(10) - 5;

        } else if (timeout < 600) {
            randomLong = random.nextInt(60) - 30;
        } else {
            randomLong = random.nextInt(300) - 150;
        }
        return timeout - randomLong;
    }
}
