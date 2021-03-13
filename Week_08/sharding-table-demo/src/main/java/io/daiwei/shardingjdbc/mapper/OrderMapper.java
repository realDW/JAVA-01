package io.daiwei.shardingjdbc.mapper;

import io.daiwei.shardingjdbc.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Daiwei on 2021/3/13
 */
public interface OrderMapper {

    void insertOrder(Order order);

    void insertBatch(@Param("list") List<Order> orderList);

    Order selectByUserId(Long userId);

    Order selectByOrderId(Long goodId);

    List<Order> selectLimit100(Integer idx);

    void delOrder(Long orderId, Long userId);
}
