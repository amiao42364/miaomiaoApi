package cn.miaomiao.api.utils;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author miaomiao
 * @date 2019/4/29 18:08
 */
public class EsRestUtil {
    private static String host;
    private static int port;
    static {
        host = PropertiesUtil.getInstance().get("elasticsearch.host");
        port = Integer.parseInt(PropertiesUtil.getInstance().get("elasticsearch.port"));
    }

    private static RestHighLevelClient client = null;

    public static RestHighLevelClient getClient(){
        if(client != null){
            return client;
        }else {
            synchronized(EsRestUtil.class){
                client = new RestHighLevelClient(
                        RestClient.builder(new HttpHost(host, port, "http")));
                return client;
            }
        }
    }
}
