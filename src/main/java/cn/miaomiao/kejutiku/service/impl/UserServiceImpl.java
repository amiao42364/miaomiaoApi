package cn.miaomiao.kejutiku.service.impl;

import cn.miaomiao.kejutiku.constant.LogConstant;
import cn.miaomiao.kejutiku.constant.RedisConstant;
import cn.miaomiao.kejutiku.dao.UserInfoDao;
import cn.miaomiao.kejutiku.dao.UserLoginDao;
import cn.miaomiao.kejutiku.entity.UserInfo;
import cn.miaomiao.kejutiku.entity.UserLogin;
import cn.miaomiao.kejutiku.exception.DaoException;
import cn.miaomiao.kejutiku.exception.HashException;
import cn.miaomiao.kejutiku.exception.ServiceException;
import cn.miaomiao.kejutiku.model.UserRo;
import cn.miaomiao.kejutiku.model.UserVo;
import cn.miaomiao.kejutiku.service.UserService;
import cn.miaomiao.kejutiku.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/22 15:46
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserLoginDao userLoginDao;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据id获取用户登录对象
     *
     * @param id id
     * @return UserLogin
     */
    @Override
    public UserLogin getUserById(Long id) {
        return userLoginDao.selectById(id);
    }

    /**
     * 根据id获取用户登录对象
     *
     * @param ids ids
     * @return List<UserLogin>
     */
    @Override
    public List<UserLogin> getUsersByIds(List<Long> ids) {
        if (VerifyEmptyUtil.isEmpty(ids)) {
            return null;
        }
        QueryWrapper<UserLogin> qw = new QueryWrapper<>();
        qw.in("id", ids);
        return userLoginDao.selectList(qw);
    }

    /**
     * 查询
     *
     * @param user user
     * @return user
     */
    @Override
    public UserLogin get(UserLogin user) {
        QueryWrapper<UserLogin> qw = getQueryWrapper(user);
        qw.last("limit 1");
        return userLoginDao.selectOne(qw);
    }

    /**
     * 查询
     *
     * @param token token
     * @return user
     */
    @Override
    public UserVo get(String token) {
        String userId = redisUtil.get(RedisConstant.TOKEN_KEY_PREFIX + token);
        return userLoginDao.getInfo(Long.parseLong(userId));
    }

    /**
     * 注册用户
     *
     * @param user user
     */
    @Override
    @Transactional(rollbackFor = DaoException.class)
    public void register(UserRo user) {
        // 先判断是否已存在该用户
        UserLogin login = get(new UserLogin(user.getUsername()));
        if (login != null) {
            throw new ServiceException("已存在该用户");
        }

        login = new UserLogin(UUID.getInstance().id());
        login.setUsername(user.getUsername());
        login.setSalt(HashUtil.getInstance().getSalt());
        login.setMobile(user.getMobile());
        login.setMail(user.getMail());
        login.setPassword(HashUtil.getInstance().sha256(user.getPassword() + login.getSalt()));
        int result = userLoginDao.insert(login);

        // 生成用户信息
        if (result != 1) {
            throw new DaoException("注册用户失败");
        }
        UserInfo userInfo = new UserInfo(UUID.getInstance().id());
        userInfo.setUserId(login.getId());
        userInfo.setNickName(login.getUsername());
        userInfo.setSex(2);
        result = userInfoDao.insert(userInfo);
        if (result != 1) {
            throw new DaoException("注册用户失败");
        }
    }

    /**
     * 登录验证
     *
     * @param user     user
     * @param password password
     * @return token
     */
    @Override
    public String auth(UserLogin user, String password) {
        String auth = HashUtil.getInstance().sha256(password + user.getSalt());
        if (auth.equals(user.getPassword())) {
            // 验证通过，生成token
            try {
                String token = HmacShaUtil.getInstance().encrypt(user.getUsername(), HmacShaUtil.getInstance().createHmacKey());
                redisUtil.set(RedisConstant.TOKEN_KEY_PREFIX + token, user.getId().toString(), RedisConstant.TOKEN_TIMEOUT);
                return token;
            } catch (HashException e) {
                return null;
            }
        }
        return null;
    }

    private QueryWrapper<UserLogin> getQueryWrapper(UserLogin user) {
        QueryWrapper<UserLogin> qw = new QueryWrapper<>();
        if (user.getId() != null) {
            qw.eq("id", user.getId());
        }
        if (VerifyEmptyUtil.isNotEmpty(user.getUsername())) {
            qw.eq("username", user.getUsername());
        }
        if (VerifyEmptyUtil.isNotEmpty(user.getMobile())) {
            qw.eq("mobile", user.getMobile());
        }
        if (VerifyEmptyUtil.isNotEmpty(user.getMail())) {
            qw.eq("mail", user.getMail());
        }
        return qw;
    }
}
