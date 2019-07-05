package com.wwx.minishopshopmanager.receive;

import com.wwx.minishop.entity.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class ActivemqReceive {

    private final RestTemplate restTemplate;

    public ActivemqReceive(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @JmsListener(destination = "new-order")
    public void receiveNewOrder(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                String orderNumber = textMessage.getText();
                Order order = restTemplate.getForObject("http://MINISHOP-ORDER/order/findWithOrderNumber?orderNumber="+orderNumber,Order.class);
                if(order!=null){
                    System.out.println(order);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }
}
