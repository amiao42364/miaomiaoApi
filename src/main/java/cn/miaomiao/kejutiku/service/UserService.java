package cn.miaomiao.kejutiku.service;

import cn.miaomiao.kejutiku.entity.UserLogin;
import cn.miaomiao.kejutiku.model.UserRo;
import cn.miaomiao.kejutiku.model.UserVo;

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
     * 注册用户
     * @param user user
     */
    void register(UserRo user);

    /**
     * 登录
     * @param user user
     * @param password password
     * @return token
     */
    String auth(UserLogin user, String password);
}
