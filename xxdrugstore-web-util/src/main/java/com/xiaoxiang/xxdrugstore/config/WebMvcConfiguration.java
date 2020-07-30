package com.xiaoxiang.xxdrugstore.config;


import com.xiaoxiang.xxdrugstore.interceptors.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/error");
        //.excludePathPatterns("/error");不拦截首页的/error请求，比如图片加载错误之类的
        super.addInterceptors(registry);
    }
}
