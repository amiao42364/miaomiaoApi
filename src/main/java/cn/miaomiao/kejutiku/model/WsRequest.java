package cn.miaomiao.kejutiku.model;

import lombok.Data;

/**
 * websocket请求体
 * @author miaomiao
 * @date 2019/4/29 9:48
 */
@Data
public class WsRequest {
    /**
     * 查询类型
     */
    private String type;

    /**
     * 操作内容
     */
    private String content;
}
