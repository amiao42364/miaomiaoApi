package cn.miaomiao.kejutiku.service;

import cn.miaomiao.kejutiku.entity.es.BaseEsData;
import cn.miaomiao.kejutiku.entity.es.NiShuiHan;

import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/29 11:33
 */
public interface KeJuTiKuService {

    /**
     * 查询题目
     * @param title 题目
     * @param gameFlag  游戏类型
     * @return list
     */
    List<BaseEsData> search(String title, String gameFlag);

    /**
     * 向es写入数据
     * @param obj obj
     * @param gameFlag 游戏类型
     */
    void save(NiShuiHan obj, String gameFlag);
}
