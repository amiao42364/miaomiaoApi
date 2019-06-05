package cn.miaomiao.api.constant;

/**
 * 自定义状态码
 *
 * @author miaomiao
 * @date 2019/4/18 18:24
 */
@SuppressWarnings("unused")
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 用户不存在或账号密码错误
     */
    USER_NOT_FOUND_OR_PASSWORD_ERROR(1001, "用户不存在或账号密码错误"),
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(1002, "用户未登录"),
    /**
     * 用户名已存在
     */
    USER_EXIST(1003, "用户名已存在"),
    /**
     * 参数错误
     */
    PARAM_ERROR(2000, "参数错误"),
    /**
     * 文件不存在
     */
    FILE_NOT_FIND_ERROR(2001, "文件不存在"),
    /**
     * hash异常
     */
    HASH_ERROR(3000, "hash异常"),
    /**
     * 超时异常
     */
    TIMEOUT_ERROR(4000, "连接超时"),
    /**
     * redis连接超时
     */
    REDIS_TIMEOUT_ERROR(4100, "redis连接超时"),
    /**
     * 雀魂牌谱不存在
     */
    MAJSOUL_NOT_FIND(5100, "雀魂牌谱不存在"),
    /**
     * 雀魂牌谱上传失败
     */
    MAJSOUL_UPLOAD_FAILED(5101, "雀魂牌谱上传失败"),
    /**
     * 参数错误
     */
    UNKNOWN_ERROR(9999, "未知错误");

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
