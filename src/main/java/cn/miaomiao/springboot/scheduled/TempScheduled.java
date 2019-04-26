package cn.miaomiao.springboot.scheduled;

import cn.miaomiao.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 临时用一下，用来测试某些场景
 * @author miaomiao
 * @date 2019/4/18 14:27
 */
@EnableScheduling
@Slf4j
@Component
public class TempScheduled {

    @Resource
    private UserService userService;

    @Scheduled(fixedRate = 10 * 3600 * 1000)
    private void configureTasks() {


    }
}
