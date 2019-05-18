package cn.miaomiao.api.controller;

import cn.miaomiao.api.constant.LogConstant;
import cn.miaomiao.api.constant.ResponseCode;
import cn.miaomiao.api.entity.UserLogin;
import cn.miaomiao.api.exception.DaoException;
import cn.miaomiao.api.exception.ServiceException;
import cn.miaomiao.api.model.BaseResponse;
import cn.miaomiao.api.model.LoginVo;
import cn.miaomiao.api.model.UserVo;
import cn.miaomiao.api.service.UserService;
import cn.miaomiao.api.utils.VerifyEmptyUtil;
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
     * 判断是否登录
     */
    @RequestMapping(value = "/judge", method = RequestMethod.GET)
    public BaseResponse judge(HttpServletRequest request) {
        Object token = request.getAttribute("authToken");
        if (token == null) {
            return BaseResponse.ok();
        }
        UserVo user = userService.get(token.toString());
        if (user == null) {
            return BaseResponse.ok();
        }
        return BaseResponse.ok(user);
    }

    /**
     * 查询用户信息，仅能查询自己
     *
     * @param request request
     * @return user
     */
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse get(HttpServletRequest request) {
        Object token = request.getAttribute("authToken");
        if (token == null) {
            return BaseResponse.error(ResponseCode.USER_NOT_LOGIN);
        }
        UserVo user = userService.get(token.toString());
        return BaseResponse.ok(user);
    }

    /**
     * 修改用户信息
     *
     * @param request request
     * @return user
     */
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse update(HttpServletRequest request, @RequestBody UserVo user) {
        Object token = request.getAttribute("authToken");
        if (token == null) {
            return BaseResponse.error(ResponseCode.USER_NOT_LOGIN);
        }
        try {
            userService.update(token.toString(), user);
        } catch (ServiceException | DaoException e) {
            log.error(LogConstant.USER_EXCEPTION + "[更新个人信息失败][token]" + token.toString());
            return BaseResponse.error(ResponseCode.UNKNOWN_ERROR);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据用户名查询用户是否存在
     *
     * @param username username
     * @return boolean
     */
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    public BaseResponse exist(@RequestParam String username) {
        UserLogin user = userService.get(new UserLogin(username));
        return BaseResponse.ok(user != null);
    }

    /**
     * 注册接口，没钱买证书就只能是http了
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody @Valid LoginVo user) {
        String token;
        try {
            token = userService.register(user);
        } catch (DaoException e) {
            log.error(LogConstant.USER_EXCEPTION + "[注册失败][username]" + user.getUsername());
            return BaseResponse.error(ResponseCode.UNKNOWN_ERROR);
        } catch (ServiceException e) {
            log.error(LogConstant.USER_EXCEPTION + e.getMessage());
            return BaseResponse.error(ResponseCode.USER_EXIST);
        }
        if (VerifyEmptyUtil.isEmpty(token)) {
            return BaseResponse.error(ResponseCode.USER_NOT_FOUND_OR_PASSWORD_ERROR);
        }
        return BaseResponse.ok(token);
    }

    /**
     * 登录接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Valid LoginVo user) {
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
