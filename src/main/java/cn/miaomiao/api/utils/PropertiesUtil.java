package cn.miaomiao.api.utils;

import cn.miaomiao.api.constant.LogConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author miaomiao
 * @date 2019/4/23 13:43
 */
@Slf4j
public class PropertiesUtil {
    private static final String SPRINGBOOT_DEFAULT_CONFIG = "application.properties";
    private static final String SPRINGBOOT_DEFAULT_KEY = "spring.profiles.active";
    private Properties prop;

    private PropertiesUtil() {
    }

    private static PropertiesUtil properties = new PropertiesUtil();

    public static PropertiesUtil getInstance() {
        return properties;
    }

    /**
     * 读取配置文件初始化
     */
    private void init() {
        ClassPathResource resource = new ClassPathResource(SPRINGBOOT_DEFAULT_CONFIG);
        try {
            prop = PropertiesLoaderUtils.loadProperties(resource);
            if(VerifyEmptyUtil.isNotEmpty(prop.getProperty(SPRINGBOOT_DEFAULT_KEY))){
                resource = new ClassPathResource("application-" + prop.getProperty(SPRINGBOOT_DEFAULT_KEY) + ".properties");
                prop = PropertiesLoaderUtils.loadProperties(resource);
            }
        } catch (IOException e) {
            log.error(LogConstant.CONFIG_EXCEPTION + "[type：init]" + e.getMessage());
        }
    }

    public String get(String key) {
        if (prop == null) {
            init();
        }
        return prop.getProperty(key);
    }
}
