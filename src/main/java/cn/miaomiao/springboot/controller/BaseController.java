package cn.miaomiao.springboot.controller;

import cn.miaomiao.springboot.model.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一个简单demo
 * @author miaomiao
 * @date 2019/4/18 18:06
 */
@RestController
@RequestMapping("/helloWorld")
public class BaseController {

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse login(@RequestParam String username, @RequestParam String password) {

        return BaseResponse.ok("hello World!");
    }
}
