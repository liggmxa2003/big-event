package com.lwz.config;

import com.lwz.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //放行路径
                .excludePathPatterns("/user")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register");
                //拦截路径damin下的所有路径
//                .addPathPatterns("/admin/**");

    }
}
