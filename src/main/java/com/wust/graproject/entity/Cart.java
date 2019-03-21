package com.wust.graproject.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 11:31
 */
@Data
@ApiModel
public class Cart {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "商品id")
    private Integer productId;
    @ApiModelProperty(value = "数量")
    private Integer quantity;
    @ApiModelProperty(value = "是否勾选 1-已经勾选 0-未勾选")
    private Integer checked;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}