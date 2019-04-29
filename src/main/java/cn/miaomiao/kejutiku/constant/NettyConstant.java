package cn.miaomiao.kejutiku.constant;

/**
 * @author miaomiao
 * @date 2019/4/28 16:10
 */
public final class NettyConstant {
    /**
     * HttpServerCodec：将请求和应答消息解码为HTTP消息
     */
    public static final String HTTP_CODE = "http-codec";

    /**
     * HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
     */
    public static final String AGGREGATOR = "aggregator";

    /**
     * ChunkedWriteHandler：向客户端发送HTML5文件
     */
    public static final String CHUNKED_WRITE = "chunkedWrite";

    /**
     * URL后缀地址
     */
    public static final String WEBSOCKET_PATH = "/ws/keju";
}
