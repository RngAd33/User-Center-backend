package com.rngad33.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 * @url http://localhost:8080/doc.html
 */
@SpringBootApplication
@MapperScan("com.rngad33.usercenter.mapper")
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
        System.out.println("后端服务已启动>>>");
    }
}