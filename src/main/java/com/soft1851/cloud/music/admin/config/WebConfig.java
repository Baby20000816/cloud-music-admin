package com.soft1851.cloud.music.admin.config;

import com.soft1851.cloud.music.admin.interceptor.LoginInterceptor;
import com.soft1851.cloud.music.admin.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/21
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private TokenInterceptor tokenInterceptor;
    /**
     * 添加拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径可自行配置多个 可用 ，分隔开
        //JWT拦截器，放行登录和验证码接口
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/sysRole/list");
        //验证码拦截器，拦截登录接口
        registry.addInterceptor(loginInterceptor).addPathPatterns("/sysAdmin/login");
    }
}
