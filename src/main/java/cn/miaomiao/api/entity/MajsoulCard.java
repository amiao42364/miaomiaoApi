package cn.miaomiao.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 雀魂模切牌表
 * </p>
 *
 * @author miaomiao
 * @since 2019-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MajsoulCard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 麻将牌: 1m1m1m2m2m2m3m3m3m4m
     */
    private String card;

    /**
     * 本家鸣牌：1z1z1z
     */
    private String ming;

    /**
     * 下家打出去的牌：4s6s3z
     */
    private String cardNext;

    /**
     * 下家鸣牌：1z1z1z
     */
    private String mingNext;

    /**
     * 对家打出去的牌：4s6s3z
     */
    private String cardOpposite;

    /**
     * 对家鸣牌：1z1z1z
     */
    private String mingOpposite;

    /**
     * 上家打出去的牌：4s6s3z
     */
    private String cardLast;

    /**
     * 上家鸣牌：1z1z1z
     */
    private String mingLast;

    /**
     * 宝牌：1m2m
     */
    private String dora;

    /**
     * 得点：本家、下家、对家、上家，英文逗号隔开
     */
    private String score;

    /**
     * 场况：东1
     */
    private String session;

    /**
     * 牌谱类型：1、简易牌谱，只有本家13张牌考察牌效。2、高级牌谱，包括场况，其他家鸣牌情况
     */
    private Integer type;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 模切牌谱答案
     */
    @TableField(exist=false)
    private List<MajsoulCardAnswer> answers;
}
