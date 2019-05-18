package cn.miaomiao.api.exception;

import cn.miaomiao.api.constant.ResponseCode;
import cn.miaomiao.api.model.BaseResponse;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局捕获异常
 *
 * @author miaomiao
 * @date 2019/5/18 18:29
 */
@ControllerAdvice
@ResponseBody
public class ControllerException {

    /**
     * valid注解验证失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse bindExceptionHandler() {
        return BaseResponse.error(ResponseCode.PARAM_ERROR);
    }

    /**
     * valid注解验证失败
     */
    @ExceptionHandler(RedisConnectionFailureException.class)
    public BaseResponse bindExceptionHandler2() {
        return BaseResponse.error(ResponseCode.REDIS_TIMEOUT_ERROR);
    }
}
