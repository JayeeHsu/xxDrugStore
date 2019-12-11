package com.xiaoxiang.xxdrugstore.user;

import com.xiaoxiang.xxdrugstore.bean.UmsMember;
import com.xiaoxiang.xxdrugstore.user.mapper.UserMapper;
import com.xiaoxiang.xxdrugstore.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import javax.swing.*;
import java.util.List;

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
