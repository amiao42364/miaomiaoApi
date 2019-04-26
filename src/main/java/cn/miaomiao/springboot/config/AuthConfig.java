package cn.miaomiao.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author miaomiao
 * @date 2019/4/25 15:20
 */
@Configuration
@ConfigurationProperties(prefix = "allow")
@PropertySource(value = "classpath:conf/auth.properties", encoding = "utf-8")
@Data
@Component
public class AuthConfig {
    private List<String> urlList;
}
