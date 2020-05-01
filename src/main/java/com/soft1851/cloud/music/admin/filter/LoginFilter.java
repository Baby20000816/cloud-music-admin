package com.soft1851.cloud.music.admin.filter;

import com.soft1851.cloud.music.admin.handler.RequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/21
 * @Version 1.0
 */
@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
@Slf4j
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("进入过滤器");
    }

    @Override
    //核心方法
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            String url = ((HttpServletRequest) servletRequest).getRequestURI();
            //判断接口是否位导入接
            if("/resources/upload".equals(url)){
                Part file = ((HttpServletRequest) servletRequest).getPart("file");
                log.info("文件名:" + file);
            }
            requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
//            log.info(requestWrapper("file"));
        }
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
