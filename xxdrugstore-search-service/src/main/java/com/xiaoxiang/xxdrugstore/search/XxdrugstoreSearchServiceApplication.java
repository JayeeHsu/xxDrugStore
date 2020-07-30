package com.xiaoxiang.xxdrugstore.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoxiang.xxdrugstore.service.SkuService;

import io.searchbox.client.JestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XxdrugstoreSearchServiceApplication {



    public static void main(String[] args)
    {
        System.setProperty("es.set.netty.runtime.available.processors","false");
        SpringApplication.run(XxdrugstoreSearchServiceApplication.class, args);
    }





}