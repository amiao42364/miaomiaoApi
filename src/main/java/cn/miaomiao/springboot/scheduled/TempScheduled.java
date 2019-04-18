package cn.miaomiao.springboot.scheduled;

import cn.miaomiao.springboot.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 临时用一下，用来测试某些场景
 * @author miaomiao
 * @date 2019/4/18 14:27
 */
@Component
@EnableScheduling
public class TempScheduled {

    private Logger log = LoggerFactory.getLogger(TempScheduled.class);

    @Scheduled(fixedRate = 5000)
    private void configureTasks() {
        log.info("info");
        log.error("error");
    }
}
