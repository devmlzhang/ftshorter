package com.yicheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * spring boot 自定义配置类
 *
 * @author luo.yicheng
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

    /**
     * 首页视图渲染配置
     * @return adapter
     */
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("index.html").setViewName("index");
            }

        };
        return adapter;
    }
}
