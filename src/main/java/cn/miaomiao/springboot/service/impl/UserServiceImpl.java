package cn.miaomiao.springboot.service.impl;

import cn.miaomiao.springboot.constant.RedisConstant;
import cn.miaomiao.springboot.exception.HashException;
import cn.miaomiao.springboot.dao.UserLoginDao;
import cn.miaomiao.springboot.entity.UserLogin;
import cn.miaomiao.springboot.service.UserService;
import cn.miaomiao.springboot.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/22 15:46
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserLoginDao userLoginDao;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据id获取用户登录对象
     * @param id id
     * @return UserLogin
     */
    @Override
    public UserLogin getUserById(Long id) {
        return userLoginDao.selectById(id);
    }

    /**
     * 根据id获取用户登录对象
     * @param ids ids
     * @return List<UserLogin>
     */
    @Override
    public List<UserLogin> getUsersByIds(List<Long> ids) {
        if(VerifyEmptyUtil.isEmpty(ids)){
            return null;
        }
        QueryWrapper<UserLogin> qw = new QueryWrapper<>();
        qw.in("id", ids);
        return userLoginDao.selectList(qw);
    }

    @Override
    public UserLogin get(UserLogin user) {
        QueryWrapper<UserLogin> qw = getQueryWrapper(user);
        qw.last("limit 1");
        return userLoginDao.selectOne(qw);
    }

    /**
     * 注册用户
     * @param username username
     * @param password password
     * @return boolean
     */
    @Override
    public boolean register(String username, String password){
        UserLogin user = new UserLogin(UUID.getInstance().id());
        user.setUsername(username);
        user.setSalt(HashUtil.getInstance().getSalt());
        try{
            user.setPassword(HashUtil.getInstance().sha256(password + user.getSalt()));
        }catch (HashException e){
            return false;
        }
        return userLoginDao.insert(user) > 0;
    }

    /**
     * 登录
     * @param user user
     * @param password password
     * @return token
     */
    @Override
    public String auth(UserLogin user, String password) {
        String auth = HashUtil.getInstance().sha256(password + user.getSalt());
        if(auth.equals(user.getPassword())){
            // 验证通过，生成token
            try {
                String token = HmacShaUtil.getInstance().encrypt(user.getUsername(), HmacShaUtil.getInstance().createHmacKey());
                redisUtil.setObject(token, user, RedisConstant.TOKEN_TIMEOUT);
                return token;
            }catch (HashException e){
                return null;
            }
        }
        return null;
    }

    private QueryWrapper<UserLogin> getQueryWrapper(UserLogin user){
        QueryWrapper<UserLogin> qw = new QueryWrapper<>();
        if(user.getId() != null){
            qw.eq("id", user.getId());
        }
        if(VerifyEmptyUtil.isNotEmpty(user.getUsername())){
            qw.eq("username", user.getUsername());
        }
        if(VerifyEmptyUtil.isNotEmpty(user.getMobile())){
            qw.eq("mobile", user.getMobile());
        }
        if(VerifyEmptyUtil.isNotEmpty(user.getMail())){
            qw.eq("mail", user.getMail());
        }
        return qw;
    }
}
