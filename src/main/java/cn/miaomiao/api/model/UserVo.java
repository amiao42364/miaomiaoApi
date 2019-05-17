package cn.miaomiao.api.model;

import lombok.Data;

import java.util.Date;

/**
 * @author miaomiao
 * @date 2019/5/17 17:29
 */
@Data
public class UserVo {
    /**
     * 登录名(可以中文)
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String mail;

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
}
