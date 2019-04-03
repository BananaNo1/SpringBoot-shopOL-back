package com.wust.graproject.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/26 15:30
 */
@Data
@Accessors(chain = true)
public class OrderProductVo {
    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;
}
