package com.wwx.minishoporder.service.impl;

import com.wwx.minishop.entity.Order;
import com.wwx.minishoporder.dao.OrderMapper;
import com.wwx.minishoporder.service.OrderService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Date;
import java.util.List;

import static com.wwx.minishop.utils.ImageUtils.getOrderNumber;

@Service
public class OrderServiceImpl implements OrderService {


    private final JmsTemplate jmsTemplate;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(JmsTemplate jmsTemplate,OrderMapper orderMapper) {
        this.jmsTemplate = jmsTemplate;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> queryOrderWithShopId(Integer shopId) {
        return orderMapper.queryOrderWithShopId(shopId);
    }

    @Override
    public int insertOrder(Order order) {
        //生成随机订单号
        String orderNumber = getOrderNumber(order);
        order.setCreateTime(new Date());
        order.setLastEditTime(new Date());
        //设置订单状态为未接单
        order.setEnableStatus(0);
        //设置订单号
        order.setOrderNumber(orderNumber);
        if(orderMapper.insertOrder(order)>0){
            Destination destination = new ActiveMQQueue("new-order");
            jmsTemplate.send(destination, session -> session.createTextMessage(order.getOrderNumber()+""));
        }
        return orderMapper.insertOrder(order);
    }

    @Override
    public int updateOrder(Order order) {
        order.setLastEditTime(new Date());
        return orderMapper.updateOrder(order);
    }

    @Override
    public int deleteOrder(Integer shopId) {
        return 0;
    }

    @Override
    public Order queryOrderWithOrderNumber(String orderNumber) {
        return orderMapper.queryOrderWithOrderNumber(orderNumber);
    }

}
