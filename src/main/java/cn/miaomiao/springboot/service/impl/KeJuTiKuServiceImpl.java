package cn.miaomiao.springboot.service.impl;

import cn.miaomiao.springboot.constant.LogConstant;
import cn.miaomiao.springboot.entity.es.BaseEsData;
import cn.miaomiao.springboot.entity.es.NiShuiHan;
import cn.miaomiao.springboot.service.KeJuTiKuService;
import cn.miaomiao.springboot.utils.EsRestUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/29 11:44
 */
@Service
@Slf4j
public class KeJuTiKuServiceImpl implements KeJuTiKuService {

    /**
     * 查询
     * @param title 题目
     * @param gameFlag  游戏类型
     * @return list
     */
    @Override
    public List<BaseEsData> search(String title, int gameFlag) {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse res = EsRestUtil.getClient().search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error(LogConstant.ES_EXCEPTION + e.getMessage());
        }
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
