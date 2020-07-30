package com.xiaoxiang.xxdrugstore.util;


import org.springframework.beans.factory.annotation.Autowired;

public class EncryptUnctypyUtil {
    @Autowired

    public static String encryptAndUncrypt(String value,char secret){//对value进行加密,secret为密文字符
        byte[]bt=value.getBytes();
        for (int i=0;i<bt.length;i++){
            bt[i]=(byte)(bt[i]^(int)secret);//通过异或运算加密
        }
        return new String(bt,0,bt.length);//返回加密后的字符串
    }

    public static void main(String[] args) {


    }
}
