package com.xiaoxiang.xxdrugstore.passport.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.xiaoxiang.xxdrugstore.bean.UmsMember;
import com.xiaoxiang.xxdrugstore.service.CartService;
import com.xiaoxiang.xxdrugstore.service.UserService;

import com.xiaoxiang.xxdrugstore.util.HttpclientUtil;
import com.xiaoxiang.xxdrugstore.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassPortController {


    @Reference
    UserService userService;

    @RequestMapping("index")
    public String index(String ReturnUrl, ModelMap map) {
        map.put("ReturnUrl", ReturnUrl);
        return "index";
    }

    @RequestMapping("registIndex")
    public String index( ModelMap map) {
        return "registIndex";
    }

    @RequestMapping("regist")
    @ResponseBody
    public String regist(UmsMember umsMember,ModelMap map) {
       // map.put("ReturnUrl", ReturnUrl);
        String Registed =userService.addUser(umsMember);
        return Registed;

    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember, HttpServletRequest request, ModelMap map) {

        String token = "";
        //调用用户服务验证用户名和密码
        UmsMember umsMemberLogin = userService.login(umsMember);
        if (umsMemberLogin != null) {
            //登录成功

            //jwt制作token
            String memberId = umsMemberLogin.getId();
            String nickname = umsMemberLogin.getNickname();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("memberId", memberId);
            userMap.put("nickname", nickname);

            String ip = request.getHeader("x-forwarded-for");//通过nginx转发的客户端ip
            if (StringUtils.isBlank(ip)) {
                ip = request.getRemoteAddr();//从request获取ip
                if (StringUtils.isBlank(ip)) {
                    ip = "127.0.0.1";
                }
            }
            //按照设计的算法对参数进行加密后，生成token
            token = JwtUtil.encode("2019xxdrugstore", userMap, ip);//三个需要传递的分别是密钥 参数 盐值

            //将token存入redis一份
            userService.addUserToken(token, memberId);
        } else {
            //登录失败
            token = "fail";
        }

        return token;
    }


    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token, String currentIp) {
        //通过jwt校验token真假
        Map<String, String> map = new HashMap<>();

        Map<String, Object> decode = JwtUtil.decode(token, "2019xxdrugstore", currentIp);//解析token
        if (decode != null) {
            map.put("status", "success");
            map.put("memberId", (String) decode.get("memberId"));
            map.put("nickname", (String) decode.get("nickname"));
        } else {
            map.put("status", "fail");
        }


        return JSON.toJSONString(map);
    }

    @RequestMapping("vlogin")
    public String vlogin(String code, HttpServletRequest request, ModelMap map) {

        // 授权码换取access_token
        // 换取access_token
        //App Key：857847600
        //App Secret：1ba9d6a2b98a83eb5fa5f6ad94f68f58

        String s3 = "https://api.weibo.com/oauth2/access_token?";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", "857847600");
        paramMap.put("client_secret", "1ba9d6a2b98a83eb5fa5f6ad94f68f58");
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("redirect_uri", "http://passport.xxdrugstore.com:8085/vlogin");
        paramMap.put("code", code);// 授权有效期内可以使用，每新生成一次授权码，说明用户对第三方数据进行重启授权，之前的access_token和授权码全部过期
        String access_token_json = HttpclientUtil.doPost(s3, paramMap);

        Map<String, Object> access_map = JSON.parseObject(access_token_json, Map.class);

        // 用access_token和uid去微博换取用户信息
        String uid = (String) access_map.get("uid");
        String access_token = (String) access_map.get("access_token");
        String show_user_url = "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid;
        String user_json = HttpclientUtil.doGet(show_user_url);
        Map<String, Object> user_map = JSON.parseObject(user_json, Map.class);

        // 将用户信息保存数据库，用户类型设置为微博用户

        UmsMember umsMember = new UmsMember();
        umsMember.setSourceType("2");//代表为微博用户
        umsMember.setAccessCode(code);
        umsMember.setAccessToken(access_token);
        umsMember.setSourceUid((String) user_map.get("idstr"));
        umsMember.setCity((String) user_map.get("location"));
        umsMember.setNickname((String) user_map.get("screen_name"));

        String g = "0";
        String gender = (String) user_map.get("gender");
        if (gender.equals("m")) {
            g = "1";
        }
        umsMember.setGender(g);

        UmsMember umsCheck = new UmsMember();
        umsCheck.setSourceUid(umsMember.getSourceUid());
        UmsMember umsMemberCheck = userService.checkOauthUser(umsCheck);// 检查该用户(社交用户)以前是否登录过系统

        if (umsMemberCheck == null) {
            umsMember = userService.addOauthUser(umsMember);
        } else {
            umsMember = umsMemberCheck;
        }

        // 生成jwt的token，并且重定向到首页，携带该token
        String token = null;
        String memberId = umsMember.getId();// rpc的主键返回策略失效
        String nickname = umsMember.getNickname();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("memberId", memberId);// 是保存数据库后主键返回策略生成的id
        userMap.put("nickname", nickname);


        String ip = request.getHeader("x-forwarded-for");// 通过nginx转发的客户端ip
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();// 从request中获取ip
            if (StringUtils.isBlank(ip)) {
                ip = "127.0.0.1";
            }
        }

        // 按照设计的算法对参数进行加密后，生成token
        token = JwtUtil.encode("2019xxdrugstore", userMap, ip);

        // 将token存入redis一份
        userService.addUserToken(token, memberId);


        return "redirect:http://search.xxdrugstore.com:8083/index?token=" + token;
    }


}

