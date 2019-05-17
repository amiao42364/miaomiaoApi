package cn.miaomiao.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author miaomiao
 * @since 2019-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户登录表id
     */
    private Long userId;

    /**
     * 昵称，默认为用户名
     */
    private String nickName;

    /**
     * 头像地址，只保存后缀
     */
    private String imageUrl;

    /**
     * 性别：0男，1女，2其他
     */
    private Integer sex;

    /**
     * 出生年月，格式yyyy-MM-dd
     */
    private String birthday;

    /**
     * 年龄，根据出生年月计算
     */
    private Integer age;

    /**
     * 星座，根据出生年月计算
     */
    private String constellation;

    /**
     * 生肖，根据出生年月计算
     */
    private String zodiac;

    /**
     * 简介
     */
    private String brief;

    /**
     * 注册时间
     */
    private Date created;

    public UserInfo() {
    }

    public UserInfo(Long id) {
        this.id = id;
    }
}
