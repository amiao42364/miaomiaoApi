package cn.miaomiao.api.scheduled;

import cn.miaomiao.api.constant.RedisConstant;
import cn.miaomiao.api.dao.MajsoulCardDao;
import cn.miaomiao.api.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务
 *
 * @author miaomiao
 * @date 2019/4/18 14:27
 */
@EnableScheduling
@Slf4j
@Component
public class MajsoulScheduled {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MajsoulCardDao majsoulCardDao;

    /**
     * 每天凌晨1点刷新下雀魂的相关配置
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void majsoulConfig() {
        log.info("雀魂配置中……");
        // 雀魂模切牌谱总数
        Integer count = majsoulCardDao.selectCount(null);
        redisUtil.set(RedisConstant.MAJSOUL_ALL_COUNT, count.toString());
        log.info("雀魂配置结束");
    }
}
