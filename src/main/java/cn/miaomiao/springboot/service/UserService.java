package cn.miaomiao.springboot.service;

import cn.miaomiao.springboot.entity.UserLogin;

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

    UserLogin get(UserLogin user);

    /**
     * 注册用户
     * @param username username
     * @param password password
     * @return boolean
     */
    boolean register(String username, String password);

    /**
     * 登录
     * @param user user
     * @param password password
     * @return token
     */
    String auth(UserLogin user, String password);
}
