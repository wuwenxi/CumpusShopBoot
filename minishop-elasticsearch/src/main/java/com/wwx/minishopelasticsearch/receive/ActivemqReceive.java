package com.wwx.minishopelasticsearch.receive;

import com.wwx.minishop.entity.Product;
import com.wwx.minishop.entity.Shop;
import com.wwx.minishopelasticsearch.service.ProductService;
import com.wwx.minishopelasticsearch.service.ShopService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

@Component
public class ActivemqReceive {

    private final ShopService shopService;

    private final RestTemplate restTemplate;

    private final ProductService productService;

    public ActivemqReceive(ShopService shopService, ProductService productService, RestTemplate restTemplate){
        this.shopService = shopService;
        this.restTemplate = restTemplate;
        this.productService = productService;
    }

    @JmsListener(destination = "test-mq")
    public void receive(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("接收到消息："+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "shop-modify")
    public void modifyShop(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                //根据店铺id通知更新
                Integer shopId = Integer.parseInt(textMessage.getText());
                Shop shop = getShopWithShopId(shopId);
                if(shop!=null)
                    shopService.updateShop(shop);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }

    @JmsListener(destination = "shop")
    public void insertShop(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                Shop shop = getShopWithShopId(Integer.parseInt(textMessage.getText()));
                if(shop!=null)
                    shopService.insert(shop);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "product")
    public void insertProduct(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                String productId  = textMessage.getText();
                Product product = getShopWithProductId(Integer.parseInt(productId));
                if(product!=null)
                    productService.insertProduct(product);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    @JmsListener(destination = "product-modify")
    public void modifyProduct(Message message){
        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                Product product = getShopWithProductId(Integer.parseInt(textMessage.getText()));
                if(product!=null)
                    productService.updateProduct(product);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private Product getShopWithProductId(Integer productId) {
        return restTemplate.getForObject("http://MINISHOP-PROVIDER/product/getProduct/"+productId,Product.class);
    }


    private Shop getShopWithShopId(Integer shopId){
        return restTemplate.getForObject("http://MINISHOP-PROVIDER/shop/getShopById/"+shopId,Shop.class);
    }

}
