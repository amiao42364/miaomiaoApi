package cn.miaomiao.springboot.service.impl;

import cn.miaomiao.springboot.entity.es.BaseEsData;
import cn.miaomiao.springboot.entity.es.NiShuiHan;
import cn.miaomiao.springboot.service.KeJuTiKuService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/29 11:44
 */
@Service
public class KeJuTiKuServiceImpl implements KeJuTiKuService {

//    @Resource
//    private KeJuRepository repository;

    /**
     * 查询
     * @param title 题目
     * @param gameFlag  游戏类型
     * @return list
     */
    @Override
    public List<BaseEsData> search(String title, int gameFlag) {
//        Iterable<NiShuiHan> listIt = repository.findAll();
//        return Lists.newArrayList(listIt);
        return null;
    }

    /**
     * 插入
     * @param obj obj
     * @param gameFlag 游戏类型
     */
    @Override
    public void save(NiShuiHan obj, int gameFlag) {

    }
}
