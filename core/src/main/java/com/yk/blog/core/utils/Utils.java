package com.yk.blog.core.utils;

import static com.yk.blog.core.utils.ConstantValue.PROJECT_NAME;

/**
 * @author yikang
 * @date 2018/9/6
 */

public class Utils {


    public static String generatePrefix(String prifix){
        return PROJECT_NAME + ":" + prifix;
    }

}
