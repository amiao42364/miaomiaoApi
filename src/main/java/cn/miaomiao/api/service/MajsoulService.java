package cn.miaomiao.api.service;

import cn.miaomiao.api.entity.MajsoulCard;

import java.util.List;

/**
 * @author miaomiao
 * @date 2019/6/5 10:04
 */
public interface MajsoulService {

    /**
     * 获取模切牌谱
     *
     * @param type  1：随机获取，2：顺序获取
     * @param curId 当前id
     * @param limit 获取题目的数量
     * @return MajsoulCard
     */
    List<MajsoulCard> get(Integer type, Integer curId, Integer limit);

    /**
     * 上传模切牌谱
     *
     * @param card MajsoulCard
     * @return success成功
     */
    String upload(MajsoulCard card);
}
