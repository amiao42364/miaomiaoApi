package cn.miaomiao.springboot.utils;

import cn.miaomiao.springboot.constant.LogConstant;
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
    private static final String APPLICATION_PROPERTIES = "application.properties";
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
        ClassPathResource resource = new ClassPathResource(APPLICATION_PROPERTIES);
        try {
            prop = PropertiesLoaderUtils.loadProperties(resource);
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
