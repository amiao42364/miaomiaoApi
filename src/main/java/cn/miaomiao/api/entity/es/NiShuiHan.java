package cn.miaomiao.api.entity.es;

import lombok.Data;

import java.util.Date;

/**
 * 逆水寒题库存储对象
 * @author miaomiao
 * @date 2019/4/29 11:25
 */
@Data
public class NiShuiHan implements BaseEsData{

    /**
     * id
     */
    private Long id;

    /**
     * 题目
     */
    private String title;

    /**
     * 答案
     */
    private String answer;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createName;
}
