package cn.miaomiao.kejutiku.controller;

import cn.miaomiao.kejutiku.constant.ResponseCode;
import cn.miaomiao.kejutiku.model.BaseResponse;
import cn.miaomiao.kejutiku.utils.VerifyEmptyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 科举查询
 * 用websocket更好一点
 *
 * @author miaomiao
 * @date 2019/4/26 13:41
 */
@RestController
@RequestMapping("/question")
public class ExaminationController {

    /**
     * 简单查询接口
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public BaseResponse query(String title) {
        if (VerifyEmptyUtil.isEmpty(title)) {
            return BaseResponse.error(ResponseCode.PARAM_ERROR);
        }

        return BaseResponse.ok();
    }
}
