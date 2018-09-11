package com.yk.blog.core.utils;


import javax.mail.*;
import javax.mail.internet.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

import static com.yk.blog.core.constant.Constant.PROJECT_NAME;

/**
 * @author yikang
 * @date 2018/9/6
 */

public class Utils {


    public static String generatePrefix(String prifix){
        return PROJECT_NAME + ":" + prifix;
    }

    public static String generateMd5(String s){
        return textToMD5L32(s);
    }

    public static String textToMD5L32(String plainText){
        String result = null;
        //首先判断是否为空
        if(plainText == null || plainText.trim().length() == 0){
            return null;
        }
        try{
            //首先进行实例化和初始化
            MessageDigest md = MessageDigest.getInstance("MD5");
            //得到一个操作系统默认的字节编码格式的字节数组
            byte[] btInput = plainText.getBytes();
            //对得到的字节数组进行处理
            md.update(btInput);
            //进行哈希计算并返回结果
            byte[] btResult = md.digest();
            //进行哈希计算后得到的数据的长度
            StringBuffer sb = new StringBuffer();
            for(byte b : btResult){
                int bt = b&0xff;
                if(bt<16){
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }
            result = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return result;
    }

    public static String generateVerifyCode(int number){
        String result = "";
        int [] numbers = {0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int next = random.nextInt(10000);
            result += numbers[next%10];
        }
        System.out.println(result);
        return result;
    }




}
