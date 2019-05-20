package cn.miaomiao.api.controller;

import cn.miaomiao.api.constant.ResponseCode;
import cn.miaomiao.api.entity.CsgoConf;
import cn.miaomiao.api.model.BaseResponse;
import cn.miaomiao.api.service.CsgoService;
import cn.miaomiao.api.utils.VerifyEmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author miaomiao
 * @date 2019/5/20 20:30
 */
@RestController
@RequestMapping("/csgo")
@Slf4j
public class CsgoConfController {

    /**
     * csgo默认启动项配置
     */
    private static final String startUpConf = "-noforcemaccel -noforcemparms -freq 75 -32bpp -novid -console";

    @Resource
    private CsgoService csgoService;

    /**
     * 获取csgo启动项参数
     */
    @RequestMapping(value = "/startUp", method = RequestMethod.GET)
    public BaseResponse getStartUpConf(HttpServletRequest request) {
        Object token = request.getAttribute("authToken");
        if (token == null) {
            return BaseResponse.error(ResponseCode.USER_NOT_LOGIN);
        }
        CsgoConf conf = csgoService.getStartUpConf(token.toString());
        if (conf == null || VerifyEmptyUtil.isEmpty(conf.getStartUp())) {
            return BaseResponse.ok(startUpConf);
        }
        return BaseResponse.ok(conf.getStartUp());
    }

    /**
     * 更新csgo启动项参数
     */
    @RequestMapping(value = "/startUp", method = RequestMethod.POST)
    public BaseResponse updateStartUpConf(HttpServletRequest request, @RequestBody CsgoConf csgoConf) {
        Object token = request.getAttribute("authToken");
        if (token == null) {
            return BaseResponse.error(ResponseCode.USER_NOT_LOGIN);
        }
        csgoService.updateStartUpConf(token.toString(), csgoConf.getStartUp());
        return BaseResponse.ok();
    }
}
