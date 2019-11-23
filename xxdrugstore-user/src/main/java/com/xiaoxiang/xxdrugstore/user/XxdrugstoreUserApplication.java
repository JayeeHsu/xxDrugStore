package com.xiaoxiang.xxdrugstore.user;
//import org.mybatis.spring.annotation.MapperScan;//使用通用mapper了，所以不用原来的MapperScan
import com.xiaoxiang.xxdrugstore.bean.UmsMemberReceiveAddress;
import org.springframework.boot.SpringApplication;//配置启动类扫描器MapperScan，使用通用mapper的tk.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.xiaoxiang.xxdrugstore.user.mapper")
//通过使用@MapperScan可以指定要扫描的Mapper类的包的路径

public class XxdrugstoreUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxdrugstoreUserApplication.class, args);
    }
    
}
