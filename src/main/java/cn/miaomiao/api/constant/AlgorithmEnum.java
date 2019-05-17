package cn.miaomiao.api.constant;

/**
 * 算法模式:
 * 1、ECB：是一种基础的加密方式，密文被分割成分组长度相等的块（不足补齐），然后单独一个个加密，一个个输出组成密文。
 * 2、CBC：是一种循环模式，前一个分组的密文和当前分组的明文异或操作后再加密，这样做的目的是增强破解难度。
 * 3、CFB/OFB实际上是一种反馈模式，目的也是增强破解的难度。
 * 补码方式：
 * 1、PKCS5Padding：明确定义Block的大小是8位
 * 2、PKCS7Padding：对于块的大小是不确定的，可以在1-255之间
 * @author miaomiao
 * @date 2019/4/23 15:20
 */
public enum AlgorithmEnum {
    // ECB
    AES_ECB_PKCS5PADDING("AES/ECB/PKCS5Padding"),
    // CBC
    AES_CBC_PKCS5PADDING("AES/CBC/PKCS5Padding"),
    // CFB
    AES_CFB_PKCS5PADDING("AES/CFB/PKCS5Padding"),
    // OFB
    AES_OFB_PKCS5PADDING("AES/OFB/PKCS5Padding"),
    // HmacSHA1
    HMACSHA1("HmacSHA1");

    /**
     * 算法类型
     */
    private String algorithm;

    AlgorithmEnum(String algorithm){
        this.algorithm = algorithm;
    }

    public String getAlgorithm(){
        return algorithm;
    }
}
