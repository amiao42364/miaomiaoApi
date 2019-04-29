package cn.miaomiao.kejutiku.constant;

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
    SUCCESS(100, "成功"),
    /**
     * 用户不存在或账号密码错误
     */
    USER_NOT_FOUND_OR_PASSWORD_ERROR(1001, "用户不存在"),
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(1002, "用户未登录"),
    /**
     * 参数错误
     */
    PARAM_ERROR(2000, "参数错误"),
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
