package com.wust.graproject.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Product
 * @Description TODO
 * @Author leis
 * @Date 2019/2/15 17:10
 * @Version 1.0
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel
public class Product {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("产品名称")
    private String name;
    @ApiModelProperty("副标题")
    private String subtitle;
    @ApiModelProperty("主图地址")
    private String mainImage;
    @ApiModelProperty("副图地址")
    private String subImages;
    @ApiModelProperty("产品详情")
    private String detail;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("销售量")
    private Integer sold;

}
