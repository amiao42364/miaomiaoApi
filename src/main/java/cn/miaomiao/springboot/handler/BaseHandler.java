package cn.miaomiao.springboot.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty实现初始层
 *
 * @author miaomiao
 * @date 2019/4/28 16:24
 */
@Slf4j
public abstract class BaseHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof TextWebSocketFrame) {
            textDoMessage(ctx, (TextWebSocketFrame) msg);
        } else if (msg instanceof WebSocketFrame) {
            webDoMessage(ctx, (WebSocketFrame) msg);
        } else if (msg instanceof FullHttpRequest) {
            httpDoMessage(ctx, (FullHttpRequest) msg);
        }
    }

    /**
     * websocket消息
     *
     * @param ctx ctx
     * @param msg msg
     */
    abstract void webDoMessage(ChannelHandlerContext ctx, WebSocketFrame msg);

    /**
     * text
     *
     * @param ctx ctx
     * @param msg msg
     */
    abstract void textDoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    /**
     * http请求，只考虑握手
     *
     * @param ctx ctx
     * @param msg msg
     */
    abstract void httpDoMessage(ChannelHandlerContext ctx, FullHttpRequest msg);

    /**
     * 不活跃的通道 说明用户失去连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.close();
    }

    /**
     * 超过心跳时间没有收到消息
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        ctx.close();
    }
}
