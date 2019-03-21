package com.wust.graproject.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 11:48
 */
@Data
@Accessors(chain = true)
@ApiModel
public class Shipping {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "收货姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货固定电话")
    private String receiverPhone;
    @ApiModelProperty(value = "收货移动电话")
    private String receiverMobile;
    @ApiModelProperty(value = "收货省份")
    private String receiverProvince;
    @ApiModelProperty(value = "收货城市")
    private String receiverCity;
    @ApiModelProperty(value = "区/县")
    private String receiverDistrict;
    @ApiModelProperty(value = "收货详细地址")
    private String receiverAddress;
    @ApiModelProperty(value = "邮编")
    private String receiverZip;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}