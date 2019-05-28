package cn.miaomiao.api.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器
 *
 * @author miaomiao
 * @date 2019/5/18 14:18
 */
@Component
public class CorsFilter implements Filter {

    private static final String ALLOW_ORIGIN_STR = "Access-Control-Allow-Origin";
    private static final String ALLOW_ORIGIN_1 = "http://shuijiaomao.com";
    private static final String ALLOW_ORIGIN_2 = "http://www.shuijiaomao.com";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        if (ALLOW_ORIGIN_1.equals(((HttpServletRequest) req).getHeader(ALLOW_ORIGIN_STR))
                || ALLOW_ORIGIN_2.equals(((HttpServletRequest) req).getHeader(ALLOW_ORIGIN_STR))) {
            response.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) req).getHeader(ALLOW_ORIGIN_STR));
        }

        response.setHeader("Access-Control-Allow-Headers", "authToken, content-type");
        // 允许跨域的请求方法类型
        response.setHeader("Access-Control-Allow-Methods", "*");
        // 预检命令（OPTIONS）缓存时间，单位：秒
        response.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
