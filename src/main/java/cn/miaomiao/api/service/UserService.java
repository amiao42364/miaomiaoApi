package cn.miaomiao.api.service;

import cn.miaomiao.api.entity.UserLogin;
import cn.miaomiao.api.model.LoginVo;
import cn.miaomiao.api.model.UserVo;

import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/22 15:46
 */
public interface UserService {

    /**
     * 根据id获取用户登录对象
     * @param id id
     * @return UserLogin
     */
    UserLogin getUserById(Long id);

    /**
     * 根据id获取用户登录对象
     * @param ids ids
     * @return List<UserLogin>
     */
    List getUsersByIds(List<Long> ids);

    /**
     * 查询
     * @param user user
     * @return user
     */
    UserLogin get(UserLogin user);

    /**
     * 查询
     * @param token token
     * @return user
     */
    UserVo get(String token);

    /**
     * 更新
     * @param token token
     * @param userVo userVo
     * @return boolean
     */
    void update(String token, UserVo userVo);

    /**
     * 注册用户
     * @param user user
     */
    void register(LoginVo user);

    /**
     * 登录
     * @param user user
     * @param password password
     * @return token
     */
    String auth(UserLogin user, String password);
}
