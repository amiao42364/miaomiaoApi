package cn.miaomiao.api.service;


import cn.miaomiao.api.entity.CsgoConf;

/**
 * @author miaomiao
 * @date 2019/5/20 20:35
 */
public interface CsgoService {

    /**
     * 获取用户csgo启动配置信息
     * @param token token
     * @return CsgoConf
     */
    CsgoConf getStartUpConf(String token);

    /**
     * 更新用户csgo配置
     * @param token token
     * @param conf conf
     */
    void updateStartUpConf(String token, String conf);
}
