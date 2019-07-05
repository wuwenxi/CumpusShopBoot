package com.wwx.minishop.utils;

public class PathUtils {
    //获取文件的分隔符
    //private static String separator = System.getProperty("file.separator");

    //获取图片在计算机中的存储路径
    public static String getImgBasePath(){
        //获取操作系统
        String os = System.getProperty("os.name");
        String basePath;
        //若是为window操作系统
        if(os.startsWith("Windows")){
            basePath = "D:/Project/MiniShop/image";
        }else {
            basePath = "/home/Project/image";
        }
        //将路径下的"/" ，替换为系统匹配
        /*basePath = basePath.replace("/",separator);*/

        return basePath;
    }

    //获取图片在数据库存储的位置
    public static String getShopImagePath(Integer shopId){
        //图片路径为
        String ImagePath = "/upload/item/shop/"+ shopId + "/";
        return ImagePath;
    }

    public static String getPersonImgPath(Integer personId){
        return "/upload/item/personInfo"+personId+"/";
    }

    //获取商品类别图片在数据库中的存储位置
    public static String getShopCategoryImagePath(){
        String ImagePath = "/upload/item/shopCategory/";
        return ImagePath;
    }

    public static String getHeadLineImagePath(){
        String ImagePath = "/upload/item/headtitle/";
        return ImagePath;
    }
}
