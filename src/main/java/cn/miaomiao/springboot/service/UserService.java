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

    /**
     * 新增用户
     */
    void createUser(UserLogin user);
}
