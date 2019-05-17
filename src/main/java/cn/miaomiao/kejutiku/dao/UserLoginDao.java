package cn.miaomiao.kejutiku.dao;

import cn.miaomiao.kejutiku.entity.UserLogin;
import cn.miaomiao.kejutiku.model.UserVo;
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
