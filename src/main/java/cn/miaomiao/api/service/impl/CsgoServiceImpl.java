package cn.miaomiao.api.service.impl;

import cn.miaomiao.api.constant.RedisConstant;
import cn.miaomiao.api.dao.CsgoConfDao;
import cn.miaomiao.api.entity.CsgoConf;
import cn.miaomiao.api.service.CsgoService;
import cn.miaomiao.api.utils.RedisUtil;
import cn.miaomiao.api.utils.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author miaomiao
 * @date 2019/5/20 20:37
 */
@Service
public class CsgoServiceImpl implements CsgoService {

    @Resource
    private CsgoConfDao csgoConfDao;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取用户csgo启动配置信息
     * @param token token
     * @return CsgoConf
     */
    @Override
    public CsgoConf getStartUpConf(String token) {
        String userId = redisUtil.get(RedisConstant.TOKEN_KEY_PREFIX + token);
        QueryWrapper<CsgoConf> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.last("limit 1");
        return csgoConfDao.selectOne(qw);
    }

    /**
     * 更新用户csgo配置
     * @param token token
     * @param conf conf
     */
    @Override
    public void updateStartUpConf(String token, String conf) {
        String userId = redisUtil.get(RedisConstant.TOKEN_KEY_PREFIX + token);
        UpdateWrapper<CsgoConf> uw = new UpdateWrapper<>();
        uw.set("start_up", conf);
        uw.eq("user_id", userId);
        Integer result = csgoConfDao.update(new CsgoConf(), uw);
        if(result == 0){
            CsgoConf csgoConf = new CsgoConf();
            csgoConf.setId(UUID.getInstance().id());
            csgoConf.setUserId(Long.parseLong(userId));
            csgoConf.setStartUp(conf);
            csgoConfDao.insert(csgoConf);
        }
    }


}
