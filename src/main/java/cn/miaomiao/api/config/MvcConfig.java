package cn.miaomiao.api.config;

import cn.miaomiao.api.filter.AuthIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

/**
 * 拦截器
 *
 * @author miaomiao
 * @date 2019/4/25 16:19
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Resource
    private AuthIntercept authIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercept).addPathPatterns("/**")
                .excludePathPatterns("/error");
    }
}
