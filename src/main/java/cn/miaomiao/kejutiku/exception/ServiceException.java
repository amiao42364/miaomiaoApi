package cn.miaomiao.kejutiku.exception;

/**
 * @author miaomiao
 * @date 2019/5/17 17:06
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = -5639843448822119202L;

    public ServiceException(String msg) {
        super(msg);
    }
}
