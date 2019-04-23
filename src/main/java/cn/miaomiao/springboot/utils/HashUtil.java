package cn.miaomiao.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 常用hash方法
 *
 * @author miaomiao
 * @date 2019/4/22 15:10
 */
public class HashUtil {

    private final static String SHA_256 = "SHA-256";
    private final static String SHA_512 = "SHA-512";
    private final static String MD5 = "MD5";

    private Logger log = LoggerFactory.getLogger(HashUtil.class);

    private static final HashUtil INSTANCE = new HashUtil();

    private HashUtil() {

    }

    public static HashUtil getInstance() {
        return INSTANCE;
    }

    public String sha256(String msg) {
        return hash(msg, SHA_256);
    }

    public String sha512(String msg) {
        return hash(msg, SHA_512);
    }

    public String md5(String msg) {
        return hash(msg, MD5);
    }

    private String hash(String msg, String type) {
        MessageDigest messageDigest;
        byte[] bytes;
        try {
            messageDigest = MessageDigest.getInstance(type);
            bytes = messageDigest.digest(msg.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            log.error("[hash异常][type：" + type + "][msg：" + msg + "]：" + e.getMessage());
            return null;
        }
        return HexUtil.bytesToHex(bytes);
    }
}
