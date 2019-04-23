package cn.miaomiao.springboot.utils;

import cn.miaomiao.springboot.constant.AlgorithmEnum;
import cn.miaomiao.springboot.constant.LogConstant;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author miaomiao
 * @date 2019/4/23 15:55
 */
@Slf4j
public class HmacShaUtil {
    private final static HmacShaUtil INSTANCE = new HmacShaUtil();

    private HmacShaUtil() {
    }

    public static HmacShaUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 生成随机的key
     */
    public String createHmacKey() {
        try {
            //得到一个指定算法密钥的密钥生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmEnum.HMACSHA1.getAlgorithm());
            //生成一个密钥
            SecretKey secretKey = keyGenerator.generateKey();
            SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), AlgorithmEnum.HMACSHA1.getAlgorithm());
            return HexUtil.bytesToHex(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error(LogConstant.HMAC_EXCEPTION + "[type：createHmacKey]" + e.getMessage());
        }
        return null;
    }

    /**
     * 使用 HMAC-SHA1 签名方法对msg进行签名
     */
    public String encrypt(String msg, String key) {
        return encrypt(msg, AlgorithmEnum.HMACSHA1.getAlgorithm(), key);
    }

    private String encrypt(String msg, String algorithm, String key) {
        try {
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(algorithm);
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);
            //完成 Mac 操作
            byte[] output = mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return HexUtil.bytesToHex(output);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(LogConstant.HMAC_EXCEPTION + "[algorithm：" + algorithm + "][msg：" + msg + "]：" + e.getMessage());
        }
        return null;
    }
}
