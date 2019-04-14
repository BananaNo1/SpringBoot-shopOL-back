package com.wust.graproject.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MqDto {

    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "订单号")
    private Long orderNo;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "支付宝支付流水号")
    private String platformNumber;
    @ApiModelProperty(value = "支付宝支付状态")
    private String platformStatus;
}
