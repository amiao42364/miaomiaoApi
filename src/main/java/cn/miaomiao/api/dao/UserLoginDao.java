package cn.miaomiao.api.dao;

import cn.miaomiao.api.entity.UserLogin;
import cn.miaomiao.api.model.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author miaomiao
 * @date 2019/4/22 15:45
 */
public interface UserLoginDao extends BaseMapper<UserLogin> {

    /**
     * 获取用户信息
     * @param id loginId
     * @return UserVo
     */
    UserVo getInfo(@Param("id") Long id);
}
