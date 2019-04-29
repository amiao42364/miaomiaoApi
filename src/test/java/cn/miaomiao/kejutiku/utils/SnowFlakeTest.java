package cn.miaomiao.kejutiku.utils;

import org.junit.Test;

/**
 * SnowFlake算法uuid生成器生成速度测试
 * 这个垃圾电脑跑100w需要6秒
 * @author miaomiao
 * @date 2019/4/18 14:43
 */
public class SnowFlakeTest {

    @Test
    public void test(){
        SnowFlake snowFlake = new SnowFlake(2, 3);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(snowFlake.nextId());
        }

        System.out.println("100万UID共用时" + (System.currentTimeMillis() - start));

    }
}
