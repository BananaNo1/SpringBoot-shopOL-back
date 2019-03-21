package com.wust.graproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 11:45
 */
@Data
@Accessors(chain = true)
@ApiModel
public class PayInfo {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "订单号")
    private Long orderNo;
    @ApiModelProperty(value = "支付平台")
    private Integer payPlatform;
    @ApiModelProperty(value = "支付宝支付流水号")
    private String platformNumber;
    @ApiModelProperty(value = "支付宝支付状态")
    private String platformStatus;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}