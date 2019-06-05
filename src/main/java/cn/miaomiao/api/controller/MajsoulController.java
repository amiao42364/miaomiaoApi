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
     * 获取模切下一题
     *
     * @param type  1：随机获取，2：顺序获取
     * @param curId 当前id
     * @return BaseResponse
     */
    @RequestMapping(value = "/moqie", method = RequestMethod.GET)
    public BaseResponse getMajsoulCard(@RequestParam int type, @RequestParam int curId) {
        List<MajsoulCard> cards = majsoulService.get(type, curId, 10);
        if (cards.size() <= 0) {
            return BaseResponse.error(ResponseCode.MAJSOUL_NOT_FIND);
        }
        return BaseResponse.ok(cards);
    }

    @RequestMapping(value = "/moqie", method = RequestMethod.POST)
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
