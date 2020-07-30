package com.xiaoxiang.xxdrugstore.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestJwt {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("memberId","1");
        map.put("nickname","许嘉毅");
        String ip = "127.0.0.1";
        String time = new SimpleDateFormat("yyyyMMdd HHmm").format(new Date());
        String encode = JwtUtil.encode("2019xxdrugstore", map, ip + time);
        System.err.println(encode);

        //eyJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6IuiuuOWYieavhSIsIm1lbWJlcklkIjoiMSJ9.fEfMfDU-eKb8t1YDQGhw0LUM-6yJymXPzUnlIe4dvrQ
        //公共部分              私人信息部分(memberId nickname 可解码 见下方)          签名部分(不可解码，用于验证token真伪)

        String tokenUserInfo = StringUtils.substringBetween(encode, ".");
        Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
        byte[] tokenBytes = base64UrlCodec.decode(tokenUserInfo);
        String tokenJson = null;
        try {
            tokenJson = new String(tokenBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map map1 = JSON.parseObject(tokenJson, Map.class);
        System.out.println("64="+map1);


    }
}
