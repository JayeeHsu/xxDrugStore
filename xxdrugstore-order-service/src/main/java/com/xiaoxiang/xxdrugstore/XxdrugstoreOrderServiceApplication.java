package com.xiaoxiang.xxdrugstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan(basePackages = "com.xiaoxiang.xxdrugstore.order.mapper")
public class XxdrugstoreOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxdrugstoreOrderServiceApplication.class, args);
    }

}
