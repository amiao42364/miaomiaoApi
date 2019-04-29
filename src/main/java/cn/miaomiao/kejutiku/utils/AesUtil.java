package cn.miaomiao.kejutiku.utils;

import cn.miaomiao.kejutiku.constant.AlgorithmEnum;
import cn.miaomiao.kejutiku.constant.LogConstant;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author miaomiao
 * @date 2019/4/22 17:42
 */
@Slf4j
public class AesUtil {
    private static final AesUtil INSTANCE = new AesUtil();

    private SecretKeySpec key;

    private AesUtil() {
        initKey();
    }

    public static AesUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 生成密钥
     */
    private void initKey() {
        try {
            //密钥生成器
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = new SecureRandom();
            String seed = PropertiesUtil.getInstance().get("aes.seed");
            secureRandom.setSeed(HashUtil.getInstance().sha256(seed).getBytes(StandardCharsets.UTF_8));
            //默认128，获得无政策权限后可用192或256
            generator.init(128, secureRandom);
            this.key = new SecretKeySpec(generator.generateKey().getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            log.error(LogConstant.AES_EXCEPTION + "[type：init]" + e.getMessage());
        }
    }

    /**
     * 加密
     */
    public String encrypt(String msg) {
        return encrypt(msg, AlgorithmEnum.AES_ECB_PKCS5PADDING.getAlgorithm());
    }

    /**
     * 解密
     */
    public String decrypt(String msg) {
        return decrypt(msg, AlgorithmEnum.AES_ECB_PKCS5PADDING.getAlgorithm());
    }

    /**
     * 加密
     */
    public String encrypt(String msg, String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
            byte[] output = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(output);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error(LogConstant.AES_EXCEPTION + "[algorithm：" + algorithm + "][msg：" + msg + "]：" + e.getMessage());
        }
        return null;
    }

    /**
     * 解密
     */
    public String decrypt(String msg, String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, this.key);
            byte[] output = cipher.doFinal(Base64.getDecoder().decode(msg));
            return new String(output, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error(LogConstant.AES_EXCEPTION + "[algorithm：" + algorithm + "][msg：" + msg + "]：" + e.getMessage());
        }
        return null;
    }
}
