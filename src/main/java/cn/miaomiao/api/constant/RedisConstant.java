package cn.miaomiao.api.constant;

/**
 * @author miaomiao
 * @date 2019/4/25 15:42
 */
public class RedisConstant {
    /**
     * redis默认缓存时间
     */
    public static final Long DEFAULT_TIMEOUT = 3600L * 24L * 7L;

    /**
     * token缓存时间
     */
    public static final Long TOKEN_TIMEOUT = 60L * 60L * 24L * 7L;

    /**
     * 昵称修改时间间隔
     */
    public static final Long MODIFY_NICKNAME_TIMEOUT = 60L * 60L * 24L * 30L;

    /**
     * token前缀
     */
    public static final String TOKEN_KEY_PREFIX = "miaomiaoToken_";

    /**
     * 游戏类型对应elasticsearch索引
     */
    public static final String INDEX_GAME_FLAG = "indexGameFlag_";

    /**
     * 标记用户上次修改昵称的时间
     */
    public static final String FLAG_MODIFY_NICKNAME = "flagModifyNickName_";

    /**
     * 雀魂麻将模切牌谱总数
     */
    public static final String MAJSOUL_ALL_COUNT = "majsoulAllCount";
}
