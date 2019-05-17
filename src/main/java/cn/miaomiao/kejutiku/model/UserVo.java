package cn.miaomiao.kejutiku.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author miaomiao
 * @date 2019/5/17 15:35
 */
@Data
public class UserVo {
    /**
     * 登录名(可以中文)
     */
    @NotNull
    @Size(min = 2, max = 24)
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Size(min = 6, max = 12)
    private String password;
}
