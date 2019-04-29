package cn.miaomiao.springboot.handler;

import cn.miaomiao.springboot.constant.NettyConstant;
import cn.miaomiao.springboot.constant.ResponseCode;
import cn.miaomiao.springboot.entity.es.BaseEsData;
import cn.miaomiao.springboot.entity.es.NiShuiHan;
import cn.miaomiao.springboot.model.BaseResponse;
import cn.miaomiao.springboot.model.WsRequest;
import cn.miaomiao.springboot.service.KeJuTiKuService;
import cn.miaomiao.springboot.utils.PinyinUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务实现
 *
 * @author miaomiao
 * @date 2019/4/28 16:29
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class KejuHandler extends BaseHandler {

    private final static String WS_SEARCH = "wsSearch";

    @Resource
    private KeJuTiKuService keJuTiKuService;

    /**
     * websocket消息
     */
    @Override
    void webDoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof CloseWebSocketFrame) {
            ctx.channel().close();
        }
    }

    /**
     * 主要业务处理
     */
    @Override
    void textDoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        WsRequest request;
        try {
            request = JSONObject.parseObject(msg.text(), WsRequest.class);
        } catch (JSONException e) {
            BaseResponse res = BaseResponse.error(ResponseCode.PARAM_ERROR);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(res)));
            return;
        }
        if(WS_SEARCH.equals(request.getType())){
            String title = request.getContent();
            List<BaseEsData> list = keJuTiKuService.search(title, 1);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(list)));
        }
    }

    /**
     * http请求，只考虑握手
     */
    @Override
    void httpDoMessage(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess() || !req.uri().equals(NettyConstant.WEBSOCKET_PATH)) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            return;
        }
        // 构造握手响应返回ws://localhost:8080/websocket
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(NettyConstant.WEBSOCKET_PATH, null, false);
        WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(req);
        if (handShaker == null) {
            //不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), req);
        }
    }
}
