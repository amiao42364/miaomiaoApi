package cn.miaomiao.api.utils;

import cn.miaomiao.api.constant.LogConstant;
import cn.miaomiao.api.constant.StringConstant;
import cn.miaomiao.api.exception.HashException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 常用hash方法
 *
 * @author miaomiao
 * @date 2019/4/22 15:10
 */
@SuppressWarnings("unused")
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

    public String sha256(String msg) throws HashException{
        return hash(msg, SHA_256);
    }

    public String sha512(String msg) throws HashException{
        return hash(msg, SHA_512);
    }

    public String md5(String msg) throws HashException{
        return hash(msg, MD5);
    }

    private String hash(String msg, String type) {
        MessageDigest messageDigest;
        byte[] bytes;
        try {
            messageDigest = MessageDigest.getInstance(type);
            bytes = messageDigest.digest(msg.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            log.error(LogConstant.HASH_EXCEPTION + "[type：" + type + "][msg：" + msg + "]：" + e.getMessage());
            throw new HashException(LogConstant.HASH_EXCEPTION);
        }
        return HexUtil.bytesToHex(bytes);
    }

    /**
     * 生成随机盐
     * @return 随机生成的盐
     */
    public String getSalt() {
        SecureRandom random = new SecureRandom();
        //盐的长度，这里是8-12可自行调整
        int length = random.nextInt(5) + 8;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = StringConstant.ALL_CHAR.charAt(random.nextInt(StringConstant.ALL_CHAR.length()));
        }
        return new String(text);
    }
}
