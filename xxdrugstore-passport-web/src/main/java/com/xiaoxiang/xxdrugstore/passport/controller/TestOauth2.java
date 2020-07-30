package com.xiaoxiang.xxdrugstore.passport.controller;


import com.alibaba.fastjson.JSON;
import com.xiaoxiang.xxdrugstore.util.HttpclientUtil;

import java.util.HashMap;
import java.util.Map;

public class TestOauth2 {


    public  String getCode(){//1.得到授权码

        //client_id=857847600
        //redirect_uri=http://passport.xxdrugstore.com:8085/vlogin
        String s1 = HttpclientUtil.doGet("https://api.weibo.com/oauth2/authorize?client_id=857847600&response_type=code&redirect_uri=http://passport.xxdrugstore.com:8085/vlogin");
        System.out.println(s1);

        return null;
    }

    public String getAccess_token(){//2.通过授权码换取Access_token
        return  null;
    }

    public String getUser_info(){//3.得到用户信息
return null;
    }


    public static void main(String[] args) {


        //App Key：857847600
        //App Secret：1ba9d6a2b98a83eb5fa5f6ad94f68f58
        //授权回调页：http://passport.xxdrugstore.com:8085/vlogin
        //取消授权回调页：http://passport.xxdrugstore.com:8085/vlogout
        //String s1 = HttpclientUtil.doGet("https://api.weibo.com/oauth2/authorize?client_id=857847600&response_type=code&redirect_uri=http://passport.xxdrugstore.com:8085/vlogin");
        //System.out.println(s1);

        //e56ca03deb7cfc88698266741cb2da37

        String s2="http://passport.xxdrugstore.com:8085/vlogin?code=e56ca03deb7cfc88698266741cb2da37";

        //通过得到的code交换access token

        String s3="https://api.weibo.com/oauth2/access_token?client_id=857847600&client_secret=1ba9d6a2b98a83eb5fa5f6ad94f68f58&grant_type=authorization_code&redirect_uri=http://passport.xxdrugstore.com:8085/vlogin&code=9ae1849f458aa9a9ce7511fdc718e383";

        Map<String,String> paramMap=new HashMap<>();
        paramMap.put("client_id","857847600");
        paramMap.put("client_secret","1ba9d6a2b98a83eb5fa5f6ad94f68f58");
        paramMap.put("grant_type","authorization_code");
        paramMap.put("redirect_uri","http://passport.xxdrugstore.com:8085/vlogin");
        paramMap.put("code","9ae1849f458aa9a9ce7511fdc718e383");


        String access_token_json = HttpclientUtil.doPost(s3,paramMap );
    //2.00El6vUG0c28Dwa92f1caed80xkXx_
      Map<String,String> access_map= JSON.parseObject(access_token_json,Map.class);
        System.out.println(access_map.get("access_token"));

    }
}
