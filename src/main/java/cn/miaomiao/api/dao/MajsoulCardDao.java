package cn.miaomiao.api.dao;

import cn.miaomiao.api.entity.MajsoulCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author miaomiao
 * @date 2019/6/4 13:53
 */
public interface MajsoulCardDao extends BaseMapper<MajsoulCard> {

    /**
     * 获取随机数量的题目
     *
     * @param limit 数量
     * @return list
     */
    List<MajsoulCard> getRandom(@Param("limit") Integer limit);
}
