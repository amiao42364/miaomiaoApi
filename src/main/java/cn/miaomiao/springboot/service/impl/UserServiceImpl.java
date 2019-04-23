package cn.miaomiao.springboot.service.impl;

import cn.miaomiao.springboot.mapper.UserLoginMapper;
import cn.miaomiao.springboot.entity.UserLogin;
import cn.miaomiao.springboot.service.UserService;
import cn.miaomiao.springboot.utils.UUID;
import cn.miaomiao.springboot.utils.VerifyEmptyUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/22 15:46
 */
public class UserServiceImpl implements UserService {

    @Resource
    private UserLoginMapper userLoginMapper;

    @Override
    public UserLogin getUserById(Long id) {
        return userLoginMapper.selectById(id);
    }

    @Override
    public List<UserLogin> getUsersByIds(List<Long> ids) {
        if(VerifyEmptyUtil.isEmpty(ids)){
            return null;
        }
        QueryWrapper<UserLogin> qw = new QueryWrapper<>();
        qw.in("id", ids);
        return userLoginMapper.selectList(qw);
    }

    @Override
    public void createUser(UserLogin user) {
        user.setId(UUID.getInstance().id());

    }
}
