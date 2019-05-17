package cn.miaomiao.api.exception;

/**
 * @author miaomiao
 * @date 2019/4/25 19:07
 */
public class HashException extends RuntimeException {
    private static final long serialVersionUID = 1395400122394312453L;

    public HashException(String msg) {
        super(msg);
    }
}
