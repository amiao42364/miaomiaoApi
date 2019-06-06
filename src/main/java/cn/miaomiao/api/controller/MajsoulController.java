package cn.miaomiao.api.controller;

import cn.miaomiao.api.constant.ResponseCode;
import cn.miaomiao.api.constant.StringConstant;
import cn.miaomiao.api.entity.MajsoulCard;
import cn.miaomiao.api.model.BaseResponse;
import cn.miaomiao.api.service.MajsoulService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miaomiao
 * @date 2019/6/4 13:25
 */
@RestController
@RequestMapping("/majsoul")
@Slf4j
public class MajsoulController {

    @Resource
    private MajsoulService majsoulService;

    /**
     * 随机获取模切题目
     *
     * @param limit 数量
     * @return BaseResponse
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public BaseResponse getMajsoulCard(@RequestParam int limit) {
        List<MajsoulCard> cards = majsoulService.get(limit);
        if (cards == null || cards.size() <= 0) {
            return BaseResponse.error(ResponseCode.MAJSOUL_NOT_FIND);
        }
        return BaseResponse.ok(cards);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse uploadMajsoulCard(@RequestBody MajsoulCard card) {
        String result = majsoulService.upload(card);
        if (!StringConstant.SUCCESS_STR.equals(result)) {
            BaseResponse response = BaseResponse.error(ResponseCode.MAJSOUL_UPLOAD_FAILED);
            response.setContent(result);
            return response;
        }
        return BaseResponse.ok();
    }
}
