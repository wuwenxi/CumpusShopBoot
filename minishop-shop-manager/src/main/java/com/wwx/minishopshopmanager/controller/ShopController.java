package com.wwx.minishopshopmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.ImageUtils;
import com.wwx.minishop.utils.PersonInfoUtils;
import com.wwx.minishop.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import static com.wwx.minishop.utils.InsertImageUtils.addShopImg;


@RestController()
@RequestMapping("/shop")
public class ShopController {

    public static final String REST_URL_SHOP_PREFIX = "http://MINISHOP-PROVIDER/shop";

    @Autowired
    RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/modifyshop")
    public Msg ModifyShop(HttpServletRequest request){
        String shopStr = HttpServletRequestUtils.getString(request,"shop");
        Shop shop;
        try {
            shop = objectMapper.readValue(shopStr,Shop.class);
        } catch (IOException e) {
            return Msg.fail().add("msg","服务器内部错误");
        }

        //解析图片
        MultipartFile shopImage = null;
        CommonsMultipartResolver resolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断是否有文件上传
            if(resolver.isMultipart(request)){
                MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
                shopImage = servletRequest.getFile("shopImg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(shopImage!=null){
            if(!ValidateUtil.checkShopImage(shopImage.getOriginalFilename())){
                return Msg.fail().add("msg","文件格式错误！请传入图片");
            }
            Shop shopImg = restTemplate.getForObject(REST_URL_SHOP_PREFIX + "/getShopById/"+shop.getShopId(),Shop.class);
            if(shopImg!=null && shopImg.getShopImg()!=null){
                ImageUtils.deleteFileOrPath(shopImg.getShopImg());
            }
            //更新图片
            try {
                addShopImg(shop,new ImageHolder(shopImage.getName(),shopImage.getInputStream()));
            } catch (IOException e) {
                return Msg.fail().add("msg","添加店铺图片失败");
            }
        }
        shop.setLastEditTime(new Date());
        Boolean flag = restTemplate.postForObject(REST_URL_SHOP_PREFIX+"/modifyShop",shop,Boolean.class);
        if(flag!=null && flag){
            return Msg.success();
        }
        return Msg.fail().add("msg","添加店铺失败");
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/registershop")
    public Msg AddShop(HttpServletRequest request){
        //转换json数据
        String shopStr = HttpServletRequestUtils.getString(request, "shopStr");
        Shop shop;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().add("msg", "服务器内部错误");
        }

        if (shop==null){
            return Msg.fail().add("msg","请填写店铺信息");
        }

        //解析图片
        MultipartFile shopImage;
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(resolver.isMultipart(request)){
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            shopImage = servletRequest.getFile("shopImg");
        } else {
            return Msg.fail().add("msg", "上传图片不能为空");
        }

        if(shopImage!=null && !ValidateUtil.checkShopImage(shopImage.getOriginalFilename())){
            return Msg.fail().add("msg","文件格式错误！请传入图片");
        }

        try {
            //前端页面不添加用户信息
            PersonInfo info = PersonInfoUtils.getPersonInfo(request);
            //如果当前用户创建的店铺达到10个  则不能再继续创建店铺
            if(info!=null){
                List<Shop> shopList = restTemplate.getForObject(REST_URL_SHOP_PREFIX+"/findShopListWithOwner/"+info.getUserId(),List.class);
                if(shopList!=null && shopList.size()==10){
                    return Msg.fail().add("msg","已到达创建店铺上限");
                }
            }
            shop.setOwner(info);

            shop = restTemplate.postForObject(REST_URL_SHOP_PREFIX + "/addShop",shop,Shop.class);
            //添加图片路径
            if(shop!=null && shop.getShopId()!=null){
                addShopImg(shop,new ImageHolder(shopImage.getOriginalFilename(),shopImage.getInputStream()));
                shop = restTemplate.postForObject(REST_URL_SHOP_PREFIX + "/modifyShop",shop,Shop.class);
            }

            if(shop!=null && shop.getShopId()!=null){
                return Msg.success();
            }
            return Msg.fail().add("msg", "添加失败");

        } catch (Exception e) {
            return Msg.fail().add("msg","服务器内部错误");
        }
    }

    @GetMapping("/getshop/{shopId}")
    public Msg getShopById(@PathVariable("shopId") Integer shopId,Map<String,Object> map){
        if(shopId!=null){
            Shop shop = restTemplate.getForObject(REST_URL_SHOP_PREFIX + "/getShopById/" + shopId, Shop.class);
            map.put("shop",shop);
            return Msg.success().add("map",map);
        }else {
            return Msg.fail().add("msg","无效商铺");
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/getshoplist")
    public Msg getShopList(HttpServletRequest request,Map<String,Object> map){
        PersonInfo personInfo = PersonInfoUtils.getPersonInfo(request);
        try {
            if(personInfo!=null && personInfo.getUserId()!=null && personInfo.getUserId()>0){
                Shop[] shops = restTemplate.getForObject(REST_URL_SHOP_PREFIX+"/findShopListWithOwner/"+personInfo.getUserId(),Shop[].class);
                if(shops!=null && shops.length> 0 ){
                    map.put("shops",Arrays.asList(shops));
                }else {
                    return Msg.fail().add("msg","当前账户没有开设店铺");
                }
                return Msg.success().add("map",map);
            }
            return Msg.fail().add("msg","服务器内部错误");
        } catch (Exception e) {
            return Msg.fail().add("msg","服务器内部错误");
        }
    }
}
