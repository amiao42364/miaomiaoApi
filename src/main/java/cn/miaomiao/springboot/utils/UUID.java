package cn.miaomiao.springboot.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * 生成UUID
 * 机器码暂时使用配置文件，后续更新为从zookeeper获取
 * @author miaomiao
 * @date 2019/4/18 14:46
 */
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
        Long id = null;
        try{
            id = snowFlake.nextId();
        }catch (NullPointerException e){
            snowFlake = getSnowFlake();
            return id();
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    private synchronized SnowFlake getSnowFlake(){
        if(snowFlake == null){
            snowFlake = new SnowFlake(dataCenterId, machineId);
        }
        return snowFlake;
    }
}
