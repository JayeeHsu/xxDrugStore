package com.xiaoxiang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.xiaoxiang.xxware")
@MapperScan(basePackages = "com.xiaoxiang.xxware.mapper")
public class XXwareManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(XXwareManageApplication.class, args);
	}
}
