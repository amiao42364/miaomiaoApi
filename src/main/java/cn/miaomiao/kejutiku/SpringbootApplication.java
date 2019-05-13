package cn.miaomiao.kejutiku;

import cn.miaomiao.kejutiku.config.NettyBootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * springboot启动类
 *
 * @author miaomiao
 * @date 2019/4/17 17:51
 */
@SpringBootApplication
@MapperScan("cn.miaomiao.kejutiku.dao")
public class SpringbootApplication implements CommandLineRunner {

    @Resource
    private NettyBootstrap nettyBootstrap;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Override
    public void run(String... args) {
        nettyBootstrap.start();
    }
}
