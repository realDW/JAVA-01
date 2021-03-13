package io.daiwei.shardingjdbc.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Daiwei on 2021/3/13
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private Long creatorId;

    private Date createTime;

    private Long updaterId;

    private Date updateTime;

    private Boolean isDeleted;

    private Long goodId;

    private Long userId;

    private Long expressId;

    private BigDecimal actualPrice;

    private String orderCode;

    private Date orderTime;

    private Integer goodStatus;
}
