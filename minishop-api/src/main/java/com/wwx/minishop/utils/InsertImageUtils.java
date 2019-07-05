package com.wwx.minishop.utils;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishop.entity.Shop;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertImageUtils {

    private static final int IMAGE_MAX_COUNT = 6;

    //获取商品缩略图存储地址
    public static void insertProductImg(Product product, ImageHolder imageHolder){
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        String realPath = ImageUtils.generateThumbnail(imageHolder,path);
        product.setImgAddress(realPath);
    }

    //处理商品详情图
    public static List<ProductImg> insertProductImgList(Product product,List<ImageHolder> imageHolderList){
        String path = PathUtils.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> list = new ArrayList<>();
        for(ImageHolder imageHolder:imageHolderList){
            String imgAddress = ImageUtils.generateNormalImage(imageHolder,path);
            ProductImg productImg = new ProductImg(null,imgAddress,
                    null,null,new Date(),product.getProductId());
            list.add(productImg);
        }
        return list;
    }

    //处理店铺图片
    public static void addShopImg(Shop shop, ImageHolder image){
        //获取相对路径  "upload/item/shop/"+ shopId + "/"
        String path = PathUtils.getShopImagePath(shop.getShopId());
        //存储图片的绝对路径
        String realPath = ImageUtils.generateThumbnail(image,path);
        //将图片地址存入数据库
        ///upload/item/shop/3/2019040613502218813.jpg
        shop.setShopImg(realPath);
    }

    public static void createImg(String relativePath,ImageHolder imageHolder){
    }

    public static void addPersonInfoImg(PersonInfo personInfo, ImageHolder imageHolder){
        String path = PathUtils.getPersonImgPath(personInfo.getUserId());

        String realPath = ImageUtils.generateHeaderImage(imageHolder,path);

        personInfo.setProfileImg(realPath);
    }

    public static ImageHolder resolveImage(HttpServletRequest request, ImageHolder image, List<ImageHolder> productImgList) {
        try {
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            //1. 获取缩略图
            MultipartFile productImg = servletRequest.getFile("productImg");
            if (productImg != null) {
                image = new ImageHolder(productImg.getOriginalFilename(),productImg.getInputStream());
            }
            //获取详细图
            for (int i=1;i<IMAGE_MAX_COUNT;i++){
                //前端页面按顺序返回图片名0-5
                MultipartFile productImages = servletRequest.getFile("productImgs"+ i);
                if(productImages != null){
                    ImageHolder imageHolder = new ImageHolder(productImages.getOriginalFilename(),productImages.getInputStream());
                    productImgList.add(imageHolder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
