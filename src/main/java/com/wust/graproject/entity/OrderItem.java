package com.wust.graproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 11:40
 */
@Data
@ApiModel
@Accessors(chain = true)
public class OrderItem {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "订单号")
    private Long orderNo;
    @ApiModelProperty(value = "商品id")
    private Integer productId;
    @ApiModelProperty(value = "商品名称")
    private String productName;
    @ApiModelProperty(value = "商品图片")
    private String productImage;
    @ApiModelProperty(value = "生成订单时的单价")
    private BigDecimal currentUnitPrice;
    @ApiModelProperty(value = "商品数量")
    private Integer quantity;
    @ApiModelProperty(value = "商品总价")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}