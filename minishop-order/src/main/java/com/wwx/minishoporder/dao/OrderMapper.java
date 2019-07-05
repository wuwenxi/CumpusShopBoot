package com.wwx.minishoporder.dao;

import com.wwx.minishop.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> queryOrderWithShopId(Integer shopId);

    int insertOrder(Order order);

    int updateOrder(Order order);

    Order queryOrderWithOrderNumber(String orderNumber);
}
