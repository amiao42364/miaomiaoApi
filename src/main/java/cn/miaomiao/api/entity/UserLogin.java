package cn.miaomiao.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户登录表
 * </p>
 *
 * @author miaomiao
 * @since 2019-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserLogin{
    private static final long serialVersionUID = 532563364875862779L;

    private Long id;

    /**
     * 登录名(可以中文)
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 角色：1：管理员，2：普通用户
     */
    private Integer role;

    public UserLogin(Long id){
        this.id = id;
    }

    public UserLogin(String username){
        this.username = username;
    }

    public UserLogin() {

    }
}
