package com.wust.graproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 11:35
 */

@Data
@ApiModel
public class Order {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "订单号")
    private Long orderNo;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "地址id")
    private Integer shippingId;
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payment;
    @ApiModelProperty(value = "支付类型 1-在线支付")
    private Integer paymentType;
    @ApiModelProperty(value = "邮费")
    private Integer postage;
    @ApiModelProperty(value = "订单状态 0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭")
    private Integer status;
    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;
    @ApiModelProperty(value = "发货时间")
    private Date sendTime;
    @ApiModelProperty(value = "交易结束时间")
    private Date endTime;
    @ApiModelProperty(value = "交易关闭时间")
    private Date closeTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}