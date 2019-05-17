package cn.miaomiao.kejutiku.controller;

import cn.miaomiao.kejutiku.constant.LogConstant;
import cn.miaomiao.kejutiku.constant.ResponseCode;
import cn.miaomiao.kejutiku.entity.UserLogin;
import cn.miaomiao.kejutiku.exception.DaoException;
import cn.miaomiao.kejutiku.exception.HashException;
import cn.miaomiao.kejutiku.exception.ServiceException;
import cn.miaomiao.kejutiku.model.BaseResponse;
import cn.miaomiao.kejutiku.model.UserRo;
import cn.miaomiao.kejutiku.model.UserVo;
import cn.miaomiao.kejutiku.service.UserService;
import cn.miaomiao.kejutiku.utils.VerifyEmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author miaomiao
 * @date 2019/4/25 16:46
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询用户信息，仅能查询自己
     * @param request request
     * @return user
     */
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse get(HttpServletRequest request){
        Object token = request.getAttribute("authToken");
        if(token == null){
            return BaseResponse.error(ResponseCode.USER_NOT_LOGIN);
        }
        UserVo user = userService.get(token.toString());
        return BaseResponse.ok(user);
    }

    /**
     * 修改用户信息
     * @param request request
     * @return user
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse update(HttpServletRequest request, @RequestBody @Valid UserVo user){
        Object token = request.getAttribute("authToken");
        if(token == null){
            return BaseResponse.error(ResponseCode.USER_NOT_LOGIN);
        }
        return BaseResponse.ok(user);
    }

    /**
     * 根据用户名查询用户是否存在
     * @param username username
     * @return boolean
     */
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    public BaseResponse exist(@RequestParam String username){
        UserLogin user = userService.get(new UserLogin(username));
        return BaseResponse.ok(user != null);
    }

    /**
     * 注册接口，没钱买证书就只能是http了
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody @Valid UserRo user) {
        try {
            userService.register(user);
        } catch (DaoException e) {
            log.error(LogConstant.REGISTER_EXCEPTION + "[username]" + user.getUsername());
            return BaseResponse.error(ResponseCode.UNKNOWN_ERROR);
        } catch (ServiceException e) {
            log.error(LogConstant.REGISTER_EXCEPTION + e.getMessage());
            return BaseResponse.error(ResponseCode.USER_EXIST);
        }
        return BaseResponse.ok();
    }

    /**
     * 登录接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Valid UserRo user) {
        UserLogin login = userService.get(new UserLogin(user.getUsername()));
        if (login == null) {
            return BaseResponse.error(ResponseCode.USER_NOT_FOUND_OR_PASSWORD_ERROR);
        }
        String token = userService.auth(login, user.getPassword());
        if (VerifyEmptyUtil.isEmpty(token)) {
            return BaseResponse.error(ResponseCode.USER_NOT_FOUND_OR_PASSWORD_ERROR);
        }
        return BaseResponse.ok(token);
    }
}
