package cn.miaomiao.api.controller;

import cn.miaomiao.api.constant.ResponseCode;
import cn.miaomiao.api.model.BaseResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用来获取一些json数据
 *
 * @author miaomiao
 * @date 2019/5/22 9:43
 */
@RestController
@RequestMapping("/json")
@Slf4j
public class JsonController {
    private static final Map<String, String> ARK_TYPES = new HashMap<>(4);

    /**
     * 明日方舟物品材料数据
     */
    private static final String ITEMS = "items";
    /**
     * 明日方舟人物数据
     */
    private static final String CHARACTERS = "characters";
    /**
     * 明日方舟等级经验数据
     */
    private static final String LEVEL = "level";
    /**
     * 明日方舟公开招募数据
     */
    private static final String HR = "hr";

    /**
     * json数据直接放到内存
     */
    private Map<String, JSONObject> jsonMap = new HashMap<>(4);

    static {
        ARK_TYPES.put(ITEMS, "/json/ArkNightsItemData.json");
        ARK_TYPES.put(CHARACTERS, "/json/ArkNightsCharacterData.json");
        ARK_TYPES.put(LEVEL, "/json/ArkNightsLevelData.json");
        ARK_TYPES.put(HR, "/json/ArkNightsCharacterData.json");
    }

    /**
     * 获取明日方舟数据
     *
     * @return items
     */
    @RequestMapping("/ark")
    public BaseResponse getArkItems(@RequestParam String type) {
        if (jsonMap.get(type) != null) {
            return BaseResponse.ok(jsonMap.get(type));
        }

        String path = ARK_TYPES.get(type);
        if (path == null) {
            return BaseResponse.error(ResponseCode.FILE_NOT_FIND_ERROR);
        }

        ClassPathResource resource = new ClassPathResource(path);
        File file;
        JSONObject obj = null;
        try {
            file = resource.getFile();
            String fileStr = FileUtils.readFileToString(file, "utf-8");
            obj = JSONObject.parseObject(fileStr);
            if (CHARACTERS.equals(type) || HR.equals(type)) {
                for (Map.Entry<String, Object> entry : obj.entrySet()) {
                    JSONArray array = (JSONArray) entry.getValue();
                    Iterator it = array.iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        if (next instanceof JSONObject) {
                            JSONObject character = (JSONObject) next;
                            if (CHARACTERS.equals(type)) {
                                if (!(boolean) character.get("money")) {
                                    it.remove();
                                }
                            } else {
                                if (!(boolean) character.get("hr")) {
                                    it.remove();
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            log.error("[json数据异常][type]" + type + "[exception]" + e.getMessage());
        }
        jsonMap.put(type, obj);
        return BaseResponse.ok(obj);
    }
}
