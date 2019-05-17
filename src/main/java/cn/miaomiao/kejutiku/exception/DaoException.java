package cn.miaomiao.kejutiku.exception;

/**
 * 数据回滚异常
 * @author miaomiao
 * @date 2019/5/17 16:18
 */
public class DaoException extends RuntimeException {

    private static final long serialVersionUID = 4336087909234068213L;

    public DaoException(String msg) {
        super(msg);
    }
}
