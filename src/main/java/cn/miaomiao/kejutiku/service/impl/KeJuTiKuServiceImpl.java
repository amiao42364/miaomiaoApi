package cn.miaomiao.kejutiku.service.impl;

import cn.miaomiao.kejutiku.constant.LogConstant;
import cn.miaomiao.kejutiku.entity.es.BaseEsData;
import cn.miaomiao.kejutiku.entity.es.NiShuiHan;
import cn.miaomiao.kejutiku.service.KeJuTiKuService;
import cn.miaomiao.kejutiku.utils.CommonUtil;
import cn.miaomiao.kejutiku.utils.EsRestUtil;
import cn.miaomiao.kejutiku.utils.VerifyEmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author miaomiao
 * @date 2019/4/29 11:44
 */
@Service
@Slf4j
public class KeJuTiKuServiceImpl implements KeJuTiKuService {

    @Resource
    private CommonUtil commonUtil;

    /**
     * 查询
     *
     * @param title    题目
     * @param gameFlag 游戏类型
     * @return list
     */
    @Override
    public List<BaseEsData> search(String title, String gameFlag) {
        String index = commonUtil.getIndices(gameFlag);
        if (VerifyEmptyUtil.isEmpty(index)) {
            return null;
        }
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("title", title));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        List<BaseEsData> list = new ArrayList<>();
        try {
            SearchResponse res = EsRestUtil.getClient().search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : res.getHits()) {
                NiShuiHan niShuiHan = new NiShuiHan();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                niShuiHan.setAnswer(sourceAsMap.get("answer").toString());
                Map<String, HighlightField> highlightMap = hit.getHighlightFields();
                HighlightField highlight = highlightMap.get("title");
                Text[] fragments = highlight.fragments();
                String fragmentString = fragments[0].string();
                niShuiHan.setTitle(fragmentString);
                list.add(niShuiHan);
            }
        } catch (IOException | NullPointerException e) {
            log.error(LogConstant.ES_EXCEPTION + e.getMessage());
        }
        return list;
    }

    /**
     * 插入
     *
     * @param obj      obj
     * @param gameFlag 游戏类型
     */
    @Override
    public void save(NiShuiHan obj, String gameFlag) {

    }
}
