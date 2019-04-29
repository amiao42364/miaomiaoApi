package cn.miaomiao.springboot.config;

import cn.miaomiao.springboot.constant.NettyConstant;
import cn.miaomiao.springboot.handler.KejuHandler;
import cn.miaomiao.springboot.utils.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * netty服务
 *
 * @author miaomiao
 * @date 2019/4/28 16:10
 */
@Component
@Slf4j
public class NettyBootstrap {

    @Resource
    private NettyConfig nettyConfig;

    @Resource
    private KejuHandler kejuHandler;

    /**
     * boss处理客户端连接
     */
    private EventLoopGroup bossGroup;

    /**
     * work进行客户端连接之后的处理
     */
    private EventLoopGroup workGroup;

    /**
     * 启动辅助类
     */
    private ServerBootstrap bootstrap = null;

    public void start() {
        initEventPool();
        bootstrap.group(bossGroup, workGroup)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, nettyConfig.getBacklog())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        // HttpServerCodec：将请求和应答消息解码为HTTP消息
                        socketChannel.pipeline().addLast(NettyConstant.HTTP_CODE, new HttpServerCodec());
                        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
                        socketChannel.pipeline().addLast(NettyConstant.AGGREGATOR, new HttpObjectAggregator(nettyConfig.getMaxContext()));
                        // ChunkedWriteHandler：向客户端发送HTML5文件
                        socketChannel.pipeline().addLast(NettyConstant.CHUNKED_WRITE, new ChunkedWriteHandler());
                        // 进行设置心跳检测
                        socketChannel.pipeline().addLast(new IdleStateHandler(nettyConfig.getReadIdleTime(), nettyConfig.getWriteIdleTime(), nettyConfig.getAllIdleTime(), TimeUnit.SECONDS));
                        // 配置通道处理 进行业务处理
                        socketChannel.pipeline().addLast(kejuHandler);
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, nettyConfig.isKeepalive())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        bootstrap.bind(nettyConfig.getPort()).addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                log.info("【netty服务端启动成功】");
            } else {
                log.info("【netty服务端启动失败】");
            }
        });
    }

    private boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }

    /**
     * 初始化EventPool 参数
     */
    private void initEventPool() {
        bootstrap = new ServerBootstrap();
        if (useEpoll()) {
            bossGroup = new EpollEventLoopGroup(nettyConfig.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new EpollEventLoopGroup(nettyConfig.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "LINUX_WORK_" + index.incrementAndGet());
                }
            });

        } else {
            bossGroup = new NioEventLoopGroup(nettyConfig.getBossThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "BOSS_" + index.incrementAndGet());
                }
            });
            workGroup = new NioEventLoopGroup(nettyConfig.getWorkerThread(), new ThreadFactory() {
                private AtomicInteger index = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "WORK_" + index.incrementAndGet());
                }
            });
        }
    }

    /**
     * 关闭资源
     */
    @PreDestroy
    public void shutdown() {
        if (workGroup != null && bossGroup != null) {
            try {
                log.info("【netty服务端关闭资源】");
                bossGroup.shutdownGracefully().sync();
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("【netty服务端关闭资源失败】");
            }
        }
    }
}
