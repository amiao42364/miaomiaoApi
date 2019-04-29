package cn.miaomiao.springboot.utils;

import org.junit.Test;

/**
 * @author miaomiao
 * @date 2019/4/29 11:04
 */
public class PinyinTest {

    @Test
    public void test(){
        String str = "大赛的风格规范的很干净  的 ghhj地方";
        System.out.println(PinyinUtil.getInstance().getPinyin(str));
        str = "";
        System.out.println(PinyinUtil.getInstance().getPinyin(str));
        str = " ";
        System.out.println(PinyinUtil.getInstance().getPinyin(str));
    }
}
