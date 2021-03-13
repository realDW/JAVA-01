package io.daiwei.shardingjdbc.service;

import io.daiwei.shardingjdbc.pojo.Order;

import java.util.List;

/**
 * Created by Daiwei on 2021/3/13
 */
public interface OrderService {

    void insertOrder(Order order);

    void insertLoop(List<Order> orderList);

    void insertBatch(List<Order> orderList);

    Order selectByUserId(Long userId);

    Order selectByOrderId(Long orderId);

    List<Order> selectLimit100(Integer idx);


    void delOrder(Long orderId, Long userId);
}
