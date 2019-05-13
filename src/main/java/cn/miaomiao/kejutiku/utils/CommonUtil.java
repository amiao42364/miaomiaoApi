package cn.miaomiao.kejutiku.utils;

import cn.miaomiao.kejutiku.constant.RedisConstant;
import cn.miaomiao.kejutiku.dao.GameTypeDao;
import cn.miaomiao.kejutiku.entity.GameType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 一些通用方法
 *
 * @author miaomiao
 * @date 2019/5/13 14:44
 */
@Component
public class CommonUtil {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private GameTypeDao gameTypeDao;

    /**
     * 根据游戏类型获取elasticsearch对应索引
     *
     * @param gameFlag 游戏类型
     * @return 索引
     */
    public String getIndices(String gameFlag) {
        String key = RedisConstant.GAMEFLAG_INDEX + gameFlag;
        String index = redisUtil.get(key);
        if (VerifyEmptyUtil.isNotEmpty(index)) {
            return index;
        }
        QueryWrapper<GameType> qw = new QueryWrapper<>();
        qw.eq("game_flag", gameFlag);
        qw.last("limit 1");
        GameType gameType = gameTypeDao.selectOne(qw);
        if (null == gameFlag) {
            return null;
        }
        redisUtil.set(key, gameType.getGameIndex());
        return gameType.getGameIndex();
    }
}
