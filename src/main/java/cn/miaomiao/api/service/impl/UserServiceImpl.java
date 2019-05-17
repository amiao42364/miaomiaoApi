package cn.miaomiao.api.service.impl;

import cn.miaomiao.api.constant.RedisConstant;
import cn.miaomiao.api.dao.UserInfoDao;
import cn.miaomiao.api.dao.UserLoginDao;
import cn.miaomiao.api.entity.UserInfo;
import cn.miaomiao.api.entity.UserLogin;
import cn.miaomiao.api.exception.DaoException;
import cn.miaomiao.api.exception.HashException;
import cn.miaomiao.api.exception.ServiceException;
import cn.miaomiao.api.model.LoginVo;
import cn.miaomiao.api.model.UserVo;
import cn.miaomiao.api.service.UserService;
import cn.miaomiao.api.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        UserVo user = userLoginDao.getInfo(Long.parseLong(userId));
        user.setAge(DateUtil.getAge(user.getBirthday()));
        return user;
    }

    /**
     * 更新
     *
     * @param userVo userVo
     */
    @Override
    @Transactional(rollbackFor = DaoException.class)
    public void update(String token, UserVo userVo) {
        String userId = redisUtil.get(RedisConstant.TOKEN_KEY_PREFIX + token);
        if (VerifyEmptyUtil.isEmpty(userId)) {
            throw new ServiceException("更新个人信息异常");
        }
        // 修改登录信息
        if (VerifyEmptyUtil.isNotEmpty(userVo.getMobile()) || VerifyEmptyUtil.isNotEmpty(userVo.getMail())) {
            UpdateWrapper<UserLogin> uw = new UpdateWrapper<>();
            if (VerifyEmptyUtil.isNotEmpty(userVo.getMobile())) {
                uw.set("mobile", userVo.getMobile());
            }
            if (VerifyEmptyUtil.isNotEmpty(userVo.getMail())) {
                uw.set("mail", userVo.getMail());
            }
            uw.eq("id", userId);
            int result = userLoginDao.update(new UserLogin(), uw);
            if (result != 1) {
                throw new DaoException("修改登录信息失败");
            }
        }
        // 修改个人信息
        UpdateWrapper<UserInfo> uw = new UpdateWrapper<>();
        // 标记是否需要更新个人信息
        boolean flag = false;
        if (VerifyEmptyUtil.isNotEmpty(userVo.getNickName())) {
            // 昵称一个月可以修改一次
            if (!redisUtil.hasKey(RedisConstant.FLAG_MODIFY_NICKNAME + userId)) {
                flag = true;
                uw.set("nick_name", userVo.getNickName());
                redisUtil.set(RedisConstant.FLAG_MODIFY_NICKNAME + userId, DateUtil.nowDateStr(), RedisConstant.MODIFY_NICKNAME_TIMEOUT);
            }
        }
        if (VerifyEmptyUtil.isNotEmpty(userVo.getImageUrl())) {
            flag = true;
            uw.set("image_url", userVo.getImageUrl());
        }
        if (userVo.getSex() != null) {
            flag = true;
            uw.set("sex", userVo.getSex());
        }
        if (VerifyEmptyUtil.isNotEmpty(userVo.getBirthday())) {
            flag = true;
            String birthday = userVo.getBirthday();
            uw.set("birthday", birthday);
            // 根据生日修改年龄、生肖、星座
            uw.set("age", DateUtil.getAge(birthday));
            uw.set("constellation", DateUtil.getConstellation(birthday));
            uw.set("zodiac", DateUtil.getZodiac(birthday));
        }
        if (VerifyEmptyUtil.isNotEmpty(userVo.getBrief())) {
            flag = true;
            uw.set("brief", userVo.getBrief());
        }
        if (flag) {
            int result = userInfoDao.update(new UserInfo(), uw);
            if (result != 1) {
                throw new DaoException("修改个人信息失败");
            }
        }
    }

    /**
     * 注册用户
     *
     * @param user user
     */
    @Override
    @Transactional(rollbackFor = DaoException.class)
    public void register(LoginVo user) {
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
