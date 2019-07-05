package com.wwx.minishopfrontend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwx.minishop.beans.Msg;
import com.wwx.minishop.entity.Order;
import com.wwx.minishop.entity.Product;
import com.wwx.minishop.utils.HttpServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @PostMapping("/createOrder")
    public Msg createOrder(HttpServletRequest request){
        String orderStr = HttpServletRequestUtils.getString(request,"order");
        Order order;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            order = objectMapper.readValue(orderStr,Order.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Msg.fail().add("msg","提交失败");
        }
        //设置订单所属店铺
        if(order!=null && order.getProduct().getProductId()!=null) {
            Product product = restTemplate.getForObject("http://MINISHOP-PROVIDER/product/getProduct/"
                    + order.getProduct().getProductId(), Product.class);
            if (product != null) {
                order.setShop(product.getShop());
            }
        }
        Boolean flag = restTemplate.postForObject("http://MINISHOP-ORDER/order/addOrder", order, Boolean.class);
        if(flag!=null && flag){
            return Msg.success();
        }else {
            return Msg.fail().add("msg","提交失败！");
        }
    }
}
