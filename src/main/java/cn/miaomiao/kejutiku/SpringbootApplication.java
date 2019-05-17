package cn.miaomiao.kejutiku;

import cn.miaomiao.kejutiku.config.NettyBootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.Resource;

/**
 * springboot启动类
 *
 * @author miaomiao
 * @date 2019/4/17 17:51
 */
@SpringBootApplication
@MapperScan("cn.miaomiao.kejutiku.dao")
public class SpringbootApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Resource
    private NettyBootstrap nettyBootstrap;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    /**
     * 解决tomcat找不到类加载器
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootApplication.class);
    }

    /**
     * 开启netty
     */
    @Override
    public void run(String... args) {
        nettyBootstrap.start();
    }
}
