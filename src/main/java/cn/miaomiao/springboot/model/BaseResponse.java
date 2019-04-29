package cn.miaomiao.springboot.model;

import cn.miaomiao.springboot.constant.ResponseCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义返回结果
 * @author miaomiao
 * @date 2019/4/18 18:17
 */
@Data
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = -458723582419276038L;
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public BaseResponse(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public BaseResponse(ResponseCode status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = "";
    }

    public BaseResponse(ResponseCode status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static BaseResponse ok(Object content) {
        return new BaseResponse(ResponseCode.SUCCESS, content);
    }

    public static BaseResponse ok() {
        return new BaseResponse(ResponseCode.SUCCESS);
    }

    public static BaseResponse error(ResponseCode error) {
        return new BaseResponse(error);
    }
}
