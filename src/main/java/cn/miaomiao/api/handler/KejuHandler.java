package cn.miaomiao.api.handler;

import cn.miaomiao.api.constant.NettyConstant;
import cn.miaomiao.api.constant.ResponseCode;
import cn.miaomiao.api.entity.es.BaseEsData;
import cn.miaomiao.api.model.BaseResponse;
import cn.miaomiao.api.model.WsRequest;
import cn.miaomiao.api.service.KeJuTiKuService;
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

        List<BaseEsData> list = keJuTiKuService.search(request.getContent(), request.getType());
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(list)));
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

    /**
     * 不活跃的通道 说明用户失去连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        super.channelInactive(ctx);
    }

    /**
     * 超过心跳时间没有收到消息
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        super.userEventTriggered(ctx, evt);
    }
}
