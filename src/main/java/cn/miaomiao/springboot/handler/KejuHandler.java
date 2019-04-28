package cn.miaomiao.springboot.handler;

import cn.miaomiao.springboot.constant.NettyConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 业务实现
 *
 * @author miaomiao
 * @date 2019/4/28 16:29
 */
@Component
@Slf4j
public class KejuHandler extends BaseHandler {

    private WebSocketServerHandshaker handShaker;

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
        ctx.channel().writeAndFlush(new TextWebSocketFrame(msg.text()));
    }

    /**
     * http请求，只考虑握手
     */
    @Override
    void httpDoMessage(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess() || !req.uri().startsWith(NettyConstant.WEBSOCKET_PATH)) {
            ctx.channel().close();
            return;
        }
        // 构造握手响应返回ws://localhost:8080/websocket
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
        handShaker = wsFactory.newHandshaker(req);
        if (handShaker == null) {
            //不支持
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), req);
        }
    }
}
