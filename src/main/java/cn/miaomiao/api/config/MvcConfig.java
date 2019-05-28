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
    //    以下WebMvcConfigurerAdapter 比较常用的重写接口
    //    /** 解决跨域问题 **/
    //    public void addCorsMappings(CorsRegistry registry) ;
    //    /** 添加拦截器 **/
    //    void addInterceptors(InterceptorRegistry registry);
    //    /** 配置视图解析器 **/
    //    void configureViewResolvers(ViewResolverRegistry registry);
    //    /** 配置内容裁决的一些选项 **/
    //    void configureContentNegotiation(ContentNegotiationConfigurer configurer);
    //    /** 视图跳转控制器 **/
    //    void addViewControllers(ViewControllerRegistry registry);
    //    /** 静态资源处理 **/
    //    void addResourceHandlers(ResourceHandlerRegistry registry);
    //    /** 默认静态资源处理器 **/
    //    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer);

    @Resource
    private AuthIntercept authIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authIntercept).addPathPatterns("/**")
                .excludePathPatterns("/error");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("Accept, Origin, authToken, content-type")
//                .allowedMethods("PUT, POST, GET, DELETE, OPTIONS")
//                .allowedOrigins("http://shuijiaomao.com, http://www.shuijiaomao.com");
//    }
}
