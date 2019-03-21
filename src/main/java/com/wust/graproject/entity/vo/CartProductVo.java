package com.wust.graproject.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 11:17
 */
@Data
@Accessors(chain = true)
public class CartProductVo {
    private Integer id;
    private Integer userId;
    private Integer productId;
    /**
     * 购物车中此商品的数量
     */
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    /**
     * 此商品是否勾选
     */
    private Integer productChecked;
    /**
     * 限制数量的一个返回结果
     */
    private String limitQuantity;
}
