package com.xiaoxiang.xxdrugstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.xiaoxiang.xxdrugstore.user.mapper")
@SpringBootApplication
public class XxdrugstoreUserServiceApplication {

    public static void main(String[] args) {


        SpringApplication.run(XxdrugstoreUserServiceApplication.class, args);
//        UserServiceImpl usi=new UserServiceImpl();
//        List<UmsMember> umsMemberList = usi.getAllUser();//userMapper.selectAllUser();
//       System.out.println(umsMemberList);
    }

}
