package cn.miaomiao.kejutiku.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author miaomiao
 * @since 2019-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色说明
     */
    private String roleDesc;

    /**
     * 权限等级：0-9逐级递增
     */
    private Integer authority;


}
