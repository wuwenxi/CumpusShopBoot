package com.wwx.minishopshopmanager.controller;

import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Order;
import com.wwx.minishop.entity.PersonInfo;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishop.utils.PersonInfoUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/findOrderListWithOwner")
    public Msg findOrderListWithOwner(HttpServletRequest request, Map<String,Object> map){
        /*
         *      1.获取商家信息
         *      2.根据商家获取所有店铺
         *      3.根据每个店铺获取订单
         */
        PersonInfo personInfo = PersonInfoUtils.getPersonInfo(request);
        if(personInfo!=null&&personInfo.getUserId()!=null){
            Shop[] shops = restTemplate.getForObject("http://MINISHOP-PROVIDER/shop/findShopListWithOwner/"+personInfo.getUserId(),Shop[].class);
            if(shops!=null&&shops.length>0){
                List<Order> list = new ArrayList<>();
                for (Shop shop:shops){
                    Order[] orders = restTemplate.getForObject("http://MINISHOP-ORDER/order/findOrderListWithShopId/"+shop.getShopId(),Order[].class);
                    if(orders!=null && orders.length>0)
                        list.addAll(Arrays.asList(orders));
                }
                map.put("list",list);
                return Msg.success().add("map",map);
            }
        }
        return Msg.fail();
    }
}
