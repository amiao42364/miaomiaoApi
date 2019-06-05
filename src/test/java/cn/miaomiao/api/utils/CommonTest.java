package cn.miaomiao.api.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 临时测试下
 *
 * @author miaomiao
 * @date 2019/4/22 10:04
 */
public class CommonTest {
    public static void main(String[] args) {
//        System.out.println(UUID.getInstance().id());
//
//        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//        System.out.println(timeStr);
//        System.out.println(DateUtil.nowDateStr());
//
//        System.out.println(randomFileName());
//
//        System.out.println(DateUtil.getZodiac("1992-01-22"));
//        System.out.println(DateUtil.getConstellation("1992-01-22"));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
        System.out.println(RandomUtil.getRandom(5));
    }

    private static String randomFileName(){
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Random random = new Random();
        Integer rNum = random.nextInt(999999);
        if(rNum < 100000){
            rNum = rNum + 100000;
        }
        return timeStr + rNum.toString();

    }
}
