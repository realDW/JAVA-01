package io.daiwei.shardingjdbc.service.impl;

import io.daiwei.shardingjdbc.mapper.OrderMapper;
import io.daiwei.shardingjdbc.pojo.Order;
import io.daiwei.shardingjdbc.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Daiwei on 2021/3/13
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void insertOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    @Override
    @Transactional
    public void insertLoop(List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            if (i == 500) {
                throw new RuntimeException();
            }
            orderMapper.insertOrder(orderList.get(i));
        }
    }

    @Override
    public void insertBatch(List<Order> orderList) {
        orderMapper.insertBatch(orderList);
    }

    @Override
    public Order selectByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    @Override
    public Order selectByOrderId(Long orderId) {
        return orderMapper.selectByOrderId(orderId);
    }

    @Override
    public List<Order> selectLimit100(Integer idx) {
        return orderMapper.selectLimit100(idx);
    }

    @Override
    public void delOrder(Long orderId, Long userId) {
        orderMapper.delOrder(orderId, userId);
    }


}
