package cn.miaomiao.kejutiku.utils;

import cn.miaomiao.kejutiku.constant.LogConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * 生成UUID
 * 机器码暂时使用配置文件，后续更新为从zookeeper获取
 * @author miaomiao
 * @date 2019/4/18 14:46
 */
@Slf4j
public class UUID {
    private static final UUID INSTANCE = new UUID();

    private SnowFlake snowFlake;

    /**
     * 数据中心
     */
    @Value("${uuid.dataCenterId}")
    private long dataCenterId;

    /**
     * 机器标识
     */
    @Value("${uuid.machineId}")
    private long machineId;

    private UUID(){
    }

    public static UUID getInstance(){
        return INSTANCE;
    }

    /**
     * 生成uuid
     */
    public Long id(){
        if(snowFlake == null){
            init();
        }
        return snowFlake.nextId();
    }

    private synchronized void init(){
        String dataCenterStr = PropertiesUtil.getInstance().get("uuid.dataCenterId");
        String machineStr = PropertiesUtil.getInstance().get("uuid.machineId");
        try{
            this.snowFlake = new SnowFlake(Long.parseLong(dataCenterStr), Long.parseLong(machineStr));
        }catch (NumberFormatException e){
            log.error(LogConstant.UUID_EXCEPTION + "[dataCenterID]" + dataCenterStr + "machineID" + machineStr);
        }
    }
}
