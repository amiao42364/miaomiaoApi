package cn.miaomiao.kejutiku.constant;

/**
 * @author miaomiao
 * @date 2019/4/25 15:42
 */
public class RedisConstant {
    /**
     * redis默认缓存时间
     */
    public static final Long DEFAULT_TIMEOUT = 60L * 30L;

    /**
     * token缓存时间
     */
    public static final Long TOKEN_TIMEOUT = 60L * 30L;

    /**
     * 游戏类型对应elasticsearch索引
     */
    public static final String GAMEFLAG_INDEX = "gameFlag_";
}
