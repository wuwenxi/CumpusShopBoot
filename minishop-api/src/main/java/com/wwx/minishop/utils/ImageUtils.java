package com.wwx.minishop.utils;


import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Order;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class  ImageUtils {

    //找到根目录下的watermark.jpg文件
    private static String basePath = Thread.currentThread()
            .getContextClassLoader().getResource("").getPath();


    //获取当前时间  年-月-日-小时-分钟-秒钟
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //随机函数
    private static final Random random = new Random();

    /**
     *
     *                  上传文件
     *          后期将采用CommonsMultipartFile
     *                店铺图片压缩
     * @param image
     * @param path
     * @return
     */
    public static String generateThumbnail(ImageHolder image, String path){
        //获取随机文件名
        String realFileName = getRandomName();
        //获取源文件的扩展名
        String extension = getFileExtension(image.getFileName());
        //创建路径所需的文件夹  "upload/item/shop/1/"
        makeDirName(path);
        //相对路径
        String relativePath = path + realFileName + extension;
        //保存路径  根路径+ 相对路径
        File dest = new File(PathUtils.getImgBasePath() + relativePath);
        try {
            Thumbnails.of(image.getInputStream())
                    .size(250,170)
                    //取消按照比例缩放，全部图片按照
                    .keepAspectRatio(false)
                    //压缩成百分之八十
                    .outputQuality(0.8f)
                    //输出新文件
                    .toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }

        return relativePath;
    }


    /**
     *
     *
     *              普通图片压缩
     * @param image
     * @param path
     * @return
     */
    public static String generateNormalImage(ImageHolder image,String path){
        //获取随机文件名
        String realFileName = getRandomName();
        //获取源文件的扩展名
        String extension = getFileExtension(image.getFileName());
        //创建路径所需的文件夹  "upload/item/shop/1/"
        makeDirName(path);
        //相对路径
        String relativePath = path + realFileName + extension;
        //保存路径  根路径+ 相对路径
        File dest = new File(PathUtils.getImgBasePath() + relativePath);
        try {
            Thumbnails.of(image.getInputStream())
                    .size(440,440)
                    //取消按照比例缩放，全部图片按照
                    .keepAspectRatio(false)
                    //压缩成百分之八十
                    .outputQuality(0.8f)
                    //输出新文件
                    .toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }

        return relativePath;
    }

    /**
     *
     *                  商铺类别图片压缩
     *
     * @param image
     * @param path
     * @return
     */
    public static String generateShopCategoryImage(ImageHolder image,String path){
        //获取随机文件名
        String realFileName = getRandomName();
        //获取源文件的扩展名
        String extension = getFileExtension(image.getFileName());
        //创建路径所需的文件夹  "upload/item/shop/1/"
        makeDirName(path);
        //相对路径
        String relativePath = path + realFileName + extension;
        //保存路径  根路径+ 相对路径
        File dest = new File(PathUtils.getImgBasePath() + relativePath);
        try {
            Thumbnails.of(image.getInputStream())
                    .size(50,50)
                    //取消按照比例缩放，全部图片按照
                    .keepAspectRatio(false)
                    //压缩成百分之八十
                    .outputQuality(0.8f)
                    //输出新文件
                    .toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }

        return relativePath;
    }

    /**
     *                  头像图片压缩
     *
     * @param image
     * @param path
     * @return
     */
    public static String generateHeaderImage(ImageHolder image,String path){
        //获取随机文件名
        String realFileName = getRandomName();
        //获取源文件的扩展名
        String extension = getFileExtension(image.getFileName());
        //创建路径所需的文件夹  ""
        makeDirName(path);
        //相对路径
        String relativePath = path + realFileName + extension;
        //保存路径  根路径+ 相对路径
        File dest = new File(PathUtils.getImgBasePath() + relativePath);
        try {
            Thumbnails.of(image.getInputStream())
                    .size(40,40)
                    //取消按照比例缩放，全部图片按照
                    .keepAspectRatio(false)
                    //压缩成百分之八十
                    .outputQuality(0.8f)
                    //输出新文件
                    .toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }

        return relativePath;
    }

    /**
     *
     *
     *                     滚动页图片压缩
     *
     * @param image
     * @param path
     * @return
     */
    public static String generateHeadLineImage(ImageHolder image,String path){
        //获取随机文件名
        String realFileName = getRandomName();
        //获取源文件的扩展名
        String extension = getFileExtension(image.getFileName());
        //创建路径所需的文件夹  ""
        makeDirName(path);
        //相对路径
        String relativePath = path + realFileName + extension;
        //保存路径  根路径+ 相对路径
        File dest = new File(PathUtils.getImgBasePath() + relativePath);
        try {
            Thumbnails.of(image.getInputStream())
                    .size(337,200)
                    //取消按照比例缩放，全部图片按照
                    .keepAspectRatio(false)
                    //压缩成百分之八十
                    .outputQuality(0.8f)
                    //输出新文件
                    .toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }

        return relativePath;
    }

    /**
     *  每个文件的开头名字都不同，当前年月日小时分钟秒 + 五位随机数
     * @return
     */
    public static String getRandomName(){
        //大于10000 小于18999
        int randomNum = random.nextInt(9999) + 10000;
        //获取当前时间
        String nowTime = dateFormat.format(new Date());
        return nowTime + randomNum;
    }

    /**
     *
     *   生成随机的订单号
     *   2019032215391818824
     * @return
     */
    public static String getOrderNumber(Order order){
        //大于10000 小于19999
        int randomNum = random.nextInt(9999) + 10000;
        //获取当前时间
        String nowTime = dateFormat.format(new Date());
        //获取商品编号、用户编号、店铺编号
        String suffix = String.valueOf(order.getProduct().getProductId()
                + order.getShop().getShopId());
        return nowTime + randomNum + suffix;
    }

    /**
     *    获取输入流文件的扩展名
     * @param file
     * @return
     */
    private static String getFileExtension(String file){
        //截取文件名中最后一个"."后面的字符串
        return file.substring(file.lastIndexOf("."));

    }

    /**
     *   创建目标路径所需的所有文件夹
     */
    private static void makeDirName(String path) {
        String realFilePath = PathUtils.getImgBasePath() + path;
        //获取文件路径
        File dirPath = new File(realFilePath);
        if(!dirPath.exists()){
            //如果文件不存在  递归创建文件目录
            dirPath.mkdirs();
        }
    }

    /**
     *         path 是文件路径还是目录文件
     *         如是文件路径则删除当前文件
     *         如是目录路径则删除当前目录下的所有文件
     * @param path
     */
    public static void deleteFileOrPath(String path){

        File fileOrPath = new File(PathUtils.getImgBasePath() + path);

        if(fileOrPath.exists()){
            //若该路径为目录  则找出该目录下的所有文件  遍历删除
            if(fileOrPath.isDirectory()){
                File[] files = fileOrPath.listFiles();
                assert files != null;
                for(File file:files){
                    file.delete();
                }
            }
            //若为文件  则直接删除
            fileOrPath.delete();
        }
    }

}
