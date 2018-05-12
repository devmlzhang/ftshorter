package com.yicheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 *
 * 项目启动入口
 *
 * @author luo.yicheng
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class FtshorterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtshorterApplication.class, args);
    }
}
