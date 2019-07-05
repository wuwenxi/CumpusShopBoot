package com.wwx.minishoporder.controller;

import com.wwx.minishop.entity.Order;
import com.wwx.minishoporder.service.OrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     *    根据店铺获取订单列表
     * @param shopId
     * @return
     */
    @GetMapping("/findOrderListWithShopId/{shopId}")
    public List<Order> findOrderListWithShopId(@PathVariable("shopId") Integer shopId){
        if(shopId!=null&&shopId>0){
            return orderService.queryOrderWithShopId(shopId);
        }
        return null;
    }

    /**
     *
     *     新增订单
     * @param order
     * @return
     */
    @PostMapping("/addOrder")
    public Boolean addOrder(@RequestBody Order order){
        if(order!=null&&order.getName()!=null&&order.getShop()!=null){
            return orderService.insertOrder(order) > 0;
        }
        return false;
    }

    /**
     *
     *      更新订单信息
     * @param order
     * @return
     */
    @PostMapping("/modifyOrder")
    public Boolean modifyOrder(@RequestBody Order order){
        if(order!=null&&order.getShop()!=null){
            return orderService.updateOrder(order) > 0;
        }
        return false;
    }

    /**
     *          取消订单， 设置订单状态   用户端
     *
     * @param shopId
     * @return
     */
    @PostMapping("/deleteOrder/{shopId}")
    public Boolean cancelOrder(@PathVariable("shopId")Integer shopId){
        if(shopId!=null){
            return orderService.deleteOrder(shopId) > 0;
        }
        return false;
    }

    @GetMapping("/findWithOrderNumber")
    public Order findWithOrderNumber(@Param("orderNumber")String orderNumber){
        if(orderNumber!=null){
            return orderService.queryOrderWithOrderNumber(orderNumber);
        }
        return null;
    }
}
