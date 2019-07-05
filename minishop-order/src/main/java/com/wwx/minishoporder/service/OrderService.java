package com.wwx.minishoporder.service;

import com.wwx.minishop.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> queryOrderWithShopId(Integer shopId);

    int insertOrder(Order order);

    int updateOrder(Order order);

    int deleteOrder(Integer shopId);

    Order queryOrderWithOrderNumber(String orderNumber);
}
