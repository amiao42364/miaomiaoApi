package cn.miaomiao.springboot.entity.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 逆水寒题库存储对象
 * @author miaomiao
 * @date 2019/4/29 11:25
 */
@Data
@Document(indexName = "kejutiku", type = "nishuihan")
public class NiShuiHan implements BaseEsData{

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 题目
     */
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 答案
     */
    @Field(type = FieldType.Keyword)
    private String answer;

    /**
     * 题目对应的拼音
     */
    @Field(type = FieldType.Keyword)
    private String key;

    /**
     * 创建时间
     */
    @Field(index = false, type = FieldType.Keyword)
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Field(index = false, type = FieldType.Keyword)
    private Long createId;

    /**
     * 创建人名称
     */
    @Field(index = false, type = FieldType.Keyword)
    private String createName;
}
