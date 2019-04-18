package cn.miaomiao.springboot.constant;

/**
 * 自定义状态码
 *
 * @author miaomiao
 * @date 2019/4/18 18:24
 */
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(100, "成功"),
    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(-1002, "用户不存在"),
    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(-1003, "用户未登录");
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
