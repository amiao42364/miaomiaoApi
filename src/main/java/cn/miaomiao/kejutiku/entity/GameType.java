package cn.miaomiao.kejutiku.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支持的游戏类型
 * </p>
 *
 * @author miaomiao
 * @since 2019-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GameType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 游戏标记
     */
    private Integer gameFlag;

    /**
     * 对应es库的index
     */
    private String gameIndex;

    /**
     * 游戏名称
     */
    private String gameTitle;

    /**
     * 描述
     */
    private String gameDesc;


}
