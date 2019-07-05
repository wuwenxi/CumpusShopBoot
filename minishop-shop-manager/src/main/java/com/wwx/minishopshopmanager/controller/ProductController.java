package com.wwx.minishopshopmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.ProductImg;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import com.wwx.minishop.utils.ImageUtils;
import com.wwx.minishop.utils.PersonInfoUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

import static com.wwx.minishop.utils.InsertImageUtils.insertProductImg;
import static com.wwx.minishop.utils.InsertImageUtils.insertProductImgList;
import static com.wwx.minishop.utils.InsertImageUtils.resolveImage;

@RestController
@RequestMapping("/product")
public class ProductController {

    public static final String REST_URL_PRODUCT_PREFIX = "http://MINISHOP-PROVIDER/product";

    public static final String REST_URL_IMG_PREFIX = "http://MINISHOP-PROVIDER/productImg";

    @Autowired
    RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     *
     *              更新商品步骤：
     *                  1.有图片更新
     *                      1）解析图片，图片列表
     *                      2）删除原来路径下的图片，插入图片新图片，将新生成的图片地址加入到商品中
     *                      3）更新成功后，遍历删除原来的图片列表，依次插入新的图片列表，并更新图片列表地址
     *                  2.没有图片更新
     *                      直接进行商品更新操作
     *
     * @param request
     * @return
     */
    @PostMapping("/modifyProduct")
    public Msg modifyProduct(HttpServletRequest request){
        String productStr = HttpServletRequestUtils.getString(request,"product");
        Product product = null;
        try {
            product = objectMapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (product==null || (product.getShop().getShopId()==null && product.getShop().getShopId()<0)){
            return Msg.fail().add("msg","商品信息为空，请填写商品信息");
        }

        //解析缩略图
        ImageHolder image = new ImageHolder();
        List<ImageHolder> productImgList;
        // 解析图片
        productImgList = new ArrayList<>();

        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (resolver.isMultipart(request)) {
                //2. 获取详细图
                image = resolveImage(request,image,productImgList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(image!=null && image.getFileName()!=null && image.getInputStream()!=null){
            try {
                //删除原来的商品图片
                Product pro = restTemplate.getForObject(REST_URL_PRODUCT_PREFIX+"/getProduct/"+product.getProductId(),Product.class);
                if(pro!=null && pro.getImgAddress()!=null){
                    ImageUtils.deleteFileOrPath(pro.getImgAddress());
                }
                //插入更新图片
                insertProductImg(product,image);
            } catch (Exception e) {
                return Msg.fail().add("msg","更新商品图片失败");
            }
        }

        Boolean flag = restTemplate.postForObject(REST_URL_PRODUCT_PREFIX+"/modifyProduct",product,Boolean.class);
        if(flag!=null && flag){
            /**
             *    插入图片列表思路：
             *      查出原来数据库中保存的图片，
             *          1.若没有保存图片，直接进行插入操作
             *         2. 有保存图片
             *              1）删除本地保存的图片
             *              2）删除数据库记录
             *              3）插入图片列表
             *
             */
            if(productImgList!=null&&productImgList.size()>0){
                List<ProductImg> list;
                ProductImg[] productImgs = restTemplate.getForObject(REST_URL_IMG_PREFIX+"/getProductImgByProductId/"+product.getProductId(),ProductImg[].class);
                if(productImgs!=null&&productImgs.length>0){
                    list = Arrays.asList(productImgs);
                    //遍历删除本地路径下的文件
                    for (ProductImg productImg:list){
                        ImageUtils.deleteFileOrPath(productImg.getImgAddress());
                    }
                    //删除数据库记录
                    flag = restTemplate.postForObject(REST_URL_IMG_PREFIX + "/deleteProductImgsByProductId", product.getProductId(), Boolean.class);
                }

                if(flag!=null && flag){
                    //获取新的图片路径信息
                    list = insertProductImgList(product,productImgList);
                    //插入图片
                    flag = restTemplate.postForObject(REST_URL_IMG_PREFIX+"/addProductImgs",list,Boolean.class);
                    if (flag!=null && !flag){
                        return Msg.fail().add("msg", "添加商品图片列表失败");
                    }
                }
            }
            return Msg.success();
        }
        return Msg.fail().add("msg", "添加商品失败");
    }

    //商品类别及分页
    @SuppressWarnings("unchecked")
    @GetMapping("/getProductList/{pn}")
    public Msg getProductList(HttpServletRequest request, @Param("shopId")Integer shopId,
                              @PathVariable("pn")Integer pn,Map<String,Object> map) throws Exception{
        //1.查出所有当前用户下的所有店铺
        //2.通过店铺查询每个店铺下的商品
        Shop shop = PersonInfoUtils.getShop(request);

        PageMethod.startPage(pn,10);

        //若没有商品列表
        List<Product> productList = new ArrayList<>();
        List<Product> products;
        if(shopId == null){
            if(shop.getOwner()!=null && shop.getOwner().getUserId()!=null
                    && shop.getOwner().getUserId()>0){
                try {
                    List<Shop> shopList = null;
                    Shop[] shops = restTemplate.getForObject(ShopController.REST_URL_SHOP_PREFIX+"/findShopListWithOwner/"+shop.getOwner().getUserId(),Shop[].class);
                    if(shops!=null && shops.length > 0){
                        shopList = Arrays.asList(shops);
                    }
                    if(shopList!=null&&shopList.size()>0){
                        for (Shop shop1:shopList){
                            products = restTemplate.getForObject(REST_URL_PRODUCT_PREFIX + "/findProductListWithShopId/"+shop1.getShopId(),List.class);
                            if(products!=null && products.size() > 0){
                                productList.addAll(products);
                            }
                        }
                    }
                } catch (Exception e) {
                    return Msg.fail().add("msg","获取商品信息失败");
                }
            }
        }else {
            products = restTemplate.getForObject(REST_URL_PRODUCT_PREFIX + "/findProductListWithShopId/"+shopId,List.class);
            if(products!=null && products.size()>0){
                productList.addAll(products);
            }
        }

        if (productList.size()>0){
            PageInfo<Product> productPageInfo = new PageInfo<>(productList,5);
            map.put("productPageInfo",productPageInfo);
        }else {
            return Msg.fail().add("msg","店铺未创建任何商品");
        }
        return Msg.success().add("map",map);
    }

    @PostMapping("/addProduct")
    public Msg addProduct(HttpServletRequest request){
        String productStr = HttpServletRequestUtils.getString(request,"productStr");

        Product product=null;
        try {
            product = objectMapper.readValue(productStr,Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (product==null || (product.getShop().getShopId()==null && product.getShop().getShopId()<0)){
            return Msg.fail().add("msg","商品信息为空，请填写商品信息");
        }

        //解析缩略图
        ImageHolder image = null;
        List<ImageHolder> productImgList;
        // 解析图片
        productImgList = new ArrayList<>();

        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (resolver.isMultipart(request)) {
                //2. 获取详细图
                image = resolveImage(request,image,productImgList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(image==null || productImgList == null){
            return Msg.fail().add("msg","请上传商品图片");
        }
        //插入商品详情图
        insertProductImg(product,image);
        //插入商品
        Boolean flag = restTemplate.postForObject(REST_URL_PRODUCT_PREFIX+"/addProduct",product,Boolean.class);
        if(flag!=null && flag){
            //插入成功后，添加商品详情列表
            List<ProductImg> list = insertProductImgList(product, productImgList);

            flag = restTemplate.postForObject(REST_URL_IMG_PREFIX + "/addProductImgs",list,Boolean.class);
            if(flag!=null && !flag){
                return Msg.fail().add("msg", "添加商品图片列表失败");
            }
            return Msg.success();
        }
        return Msg.fail().add("msg", "添加商品失败");
    }

    @GetMapping("/getProduct/{productId}")
    public Msg getProduct(@PathVariable("productId")Integer productId,Map<String,Object> map){
        if(productId!=null && productId>0){
            Product product = restTemplate.getForObject(REST_URL_PRODUCT_PREFIX + "/getProduct/" + productId, Product.class);
            if(product!=null){
                map.put("product",product);
                return Msg.success().add("map",map);
            }
            return Msg.fail().add("msg","发生未知错误，未找到该商品！");
        }
        return Msg.fail().add("msg","发生未知错误，未找到该商品！");
    }

}
