package cn.miaomiao.api.utils;

import cn.miaomiao.api.constant.AlgorithmEnum;
import org.junit.Test;

import java.util.Date;

/**
 * @author miaomiao
 * @date 2019/4/22 11:37
 */
public class EncryptTest {

    private static final int num = 1000000;

    @Test
    public void aes() {
        String aaa = AesUtil.getInstance().encrypt("aaa");
        System.err.println(aaa);
        String bbb = AesUtil.getInstance().decrypt(aaa);
        System.err.println(bbb);
        String key = HmacShaUtil.getInstance().createHmacKey();
        System.err.println(key);
        String ccc = HmacShaUtil.getInstance().encrypt("aaa", key);
        System.err.println(ccc);
    }

    /**
     * 算法耗时测试，加密100W数据
     * 一般来说，加密时间越长安全性越好
     */
    @Test
    public void performance() {
        Date d1 = new Date();
        String[] str = new String[num];
        for (int i = 0; i < num; i++) {
            str[i] = randomStr();
        }
        Date d2 = new Date();
        System.out.println("生成数据耗时" + (d2.getTime() - d1.getTime()));

        // hash-256
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            HashUtil.getInstance().sha256(str[i]);
        }
        d2 = new Date();
        System.out.println("hash-256耗时" + (d2.getTime() - d1.getTime()));

        // hash-512
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            HashUtil.getInstance().sha512(str[i]);
        }
        d2 = new Date();
        System.out.println("hash-512耗时" + (d2.getTime() - d1.getTime()));

        // hmac
        String key = HmacShaUtil.getInstance().createHmacKey();
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            HmacShaUtil.getInstance().encrypt(str[i], key);
        }
        d2 = new Date();
        System.out.println("hmac耗时" + (d2.getTime() - d1.getTime()));

        // aes ECB
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            AesUtil.getInstance().encrypt(str[i], AlgorithmEnum.AES_ECB_PKCS5PADDING.getAlgorithm());
        }
        d2 = new Date();
        System.out.println("aes ECB耗时" + (d2.getTime() - d1.getTime()));

        // aes CBC
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            AesUtil.getInstance().encrypt(str[i], AlgorithmEnum.AES_CBC_PKCS5PADDING.getAlgorithm());
        }
        d2 = new Date();
        System.out.println("aes CBC耗时" + (d2.getTime() - d1.getTime()));

        // aes CFB
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            AesUtil.getInstance().encrypt(str[i], AlgorithmEnum.AES_CFB_PKCS5PADDING.getAlgorithm());
        }
        d2 = new Date();
        System.out.println("aes CFB耗时" + (d2.getTime() - d1.getTime()));

        // aes OFB
        d1 = new Date();
        for (int i = 0; i < num; i++) {
            AesUtil.getInstance().encrypt(str[i], AlgorithmEnum.AES_OFB_PKCS5PADDING.getAlgorithm());
        }
        d2 = new Date();
        System.out.println("aes OFB耗时" + (d2.getTime() - d1.getTime()));
    }

    private String randomStr() {
        return "abc";
    }
}
