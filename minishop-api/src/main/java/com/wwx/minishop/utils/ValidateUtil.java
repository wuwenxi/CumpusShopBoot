package com.wwx.minishop.utils;


public class ValidateUtil {

    public static boolean checkShopImage(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return ".png".equals(suffix) || ".jpg".equals(suffix);
    }
}
