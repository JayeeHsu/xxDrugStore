package com.xiaoxiang.xxdrugstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.xiaoxiang.xxdrugstore.cart.mapper")
public class XxdrugstoreCartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxdrugstoreCartServiceApplication.class, args);
    }

}
