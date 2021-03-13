package io.daiwei.shardingjdbc.service;

import io.daiwei.shardingjdbc.pojo.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Daiwei on 2021/3/13
 */
@SpringBootTest
class OrderServiceTest {

    @Resource
    private OrderService orderService;

    @Test
    void insertOrder() {
        for (int i = 0; i < 1000; i++) {
            Order order = Order.builder().orderCode(UUID.randomUUID().toString().replace("-", ""))
                    .actualPrice(BigDecimal.ONE).expressId(i + 10086L).userId(i + 667L).goodId(i + 43428L)
                    .orderTime(new Date()).build();
            orderService.insertOrder(order);
        }
    }

    @Test
    void insertLoop() {
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Order order = Order.builder().orderCode(UUID.randomUUID().toString().replace("-", ""))
                    .actualPrice(BigDecimal.ONE).expressId(i + 10086L).userId(i + 667L).goodId(i + 43428L)
                    .orderTime(new Date()).build();
            list.add(order);
        }
        orderService.insertLoop(list);
    }

    /**
     * 一个一条 1000数据的拼接sql也能分，牛逼！
     */
    @Test
    void insertBatch() {
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Order order = Order.builder().orderCode(UUID.randomUUID().toString().replace("-", ""))
                    .actualPrice(BigDecimal.ONE).expressId(i + 10086L).userId(i + 667L).goodId(i + 43428L)
                    .orderTime(new Date()).build();
            list.add(order);
        }
        orderService.insertBatch(list);
    }


    @Test
    void selectByUserId() {
        Order order = orderService.selectByUserId(888L);
        System.out.println(order);
    }

    @Test
    void selectByOrderId() {
        Order order = orderService.selectByOrderId(43888L);
        System.out.println(order);
    }

    @Test
    void selectLimit100() {
        List<Order> orders = orderService.selectLimit100(666);
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    // 更新操作的时候尽可能提供分片字段，这样可以直接命中某一个数据库的某一个表，提高操作的效率。
    @Test
    void delOrder() {
        orderService.delOrder(43439L, 3434L);
    }
}