package cn.miaomiao.springboot.scheduled;

import cn.miaomiao.springboot.utils.UUID;
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

    @Scheduled(fixedRate = 1000)
    private void configureTasks() {
        System.err.println(UUID.getInstance().id());
        System.err.println(UUID.getInstance().id());
        System.err.println(UUID.getInstance().id());
        System.err.println(UUID.getInstance().id());
    }
}
