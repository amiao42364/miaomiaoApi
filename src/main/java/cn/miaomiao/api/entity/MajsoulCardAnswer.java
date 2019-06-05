package cn.miaomiao.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 雀魂模切牌谱答案表
 * </p>
 *
 * @author miaomiao
 * @since 2019-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MajsoulCardAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 雀魂模切牌谱id
     */
    private Integer cardId;

    /**
     * 切出去的牌
     */
    private String key;

    /**
     * 为何这样切描述
     */
    private String desc;

    /**
     * 该答案支持的人数
     */
    private Integer up;

    /**
     * 该答案反对的人数
     */
    private Integer down;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date created;


}
