package cn.miaomiao.kejutiku.controller;

import cn.miaomiao.kejutiku.constant.ResponseCode;
import cn.miaomiao.kejutiku.entity.UserLogin;
import cn.miaomiao.kejutiku.model.BaseResponse;
import cn.miaomiao.kejutiku.model.UserVo;
import cn.miaomiao.kejutiku.service.UserService;
import cn.miaomiao.kejutiku.utils.VerifyEmptyUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author miaomiao
 * @date 2019/4/25 16:46
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册接口，没钱买证书就只能是http了
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestParam String username, @RequestParam String password) {
        if(VerifyEmptyUtil.isEmpty(username) || VerifyEmptyUtil.isEmpty(password)){
            return BaseResponse.error(ResponseCode.PARAM_ERROR);
        }
        if(userService.register(username, password)){
            return BaseResponse.ok();
        }
        else {
            return BaseResponse.error(ResponseCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 登录接口
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Valid UserVo user){
        UserLogin login = userService.get(new UserLogin(user.getUsername()));
        if(login == null){
            return BaseResponse.error(ResponseCode.USER_NOT_FOUND_OR_PASSWORD_ERROR);
        }
        String token = userService.auth(login, user.getPassword());
        if(VerifyEmptyUtil.isEmpty(token)){
            return BaseResponse.error(ResponseCode.USER_NOT_FOUND_OR_PASSWORD_ERROR);
        }
        return BaseResponse.ok(token);
    }

    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    public BaseResponse helloWorld(){
        return BaseResponse.ok("hello");
    }
}
