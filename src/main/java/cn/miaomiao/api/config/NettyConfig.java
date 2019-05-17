package cn.miaomiao.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author miaomiao
 * @date 2019/4/28 16:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
@PropertySource("classpath:/conf/netty.properties")
public class NettyConfig {
    /**
     * netty启动端口号
     */
    private int port;
    /**
     * netty服务boss线程数:处理客户端连接
     */
    private int bossThread;
    /**
     * netty服务work线程数:客户端连接之后的处理
     */
    private int workerThread;
    /**
     * 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
     */
    private int backlog;

    /**
     * 是否保持链接
     */
    private boolean keepalive;

    /**
     * 最大长度
     */
    private int maxContext;

    /**
     * 心跳读超时
     */
    private int readIdleTime;

    /**
     * 心跳写超时
     */
    private int writeIdleTime;

    /**
     * 心跳总超时
     */
    private int allIdleTime;
}
