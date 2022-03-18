package com.example.demo.configuration;

import com.example.demo.interceptor.LoginInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Chu
 * @create 2022-03-14-14:03
 */
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())//拦截器注册到容器中
                .addPathPatterns("/**")  //所有请求都被拦截包括静态资源
                //放行的请求 写完了再加OAO
                .excludePathPatterns("/", "/login", "/css/**", "/fonts/**", "/images/**",
                        "/js/**","/user"); //放行的请求


    }
}
