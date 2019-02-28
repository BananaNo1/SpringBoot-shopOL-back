package com.wust.graproject.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
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
@Document(indexName = "graproject", type = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 4940051959356842026L;

    @ApiModelProperty("主键id")
    @Id
    private Integer id;

    @ApiModelProperty("分类id")
    @Field
    private Integer categoryId;

    @ApiModelProperty("产品名称")
    @Field(searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String name;

    @ApiModelProperty("副标题")
    @Field
    private String subtitle;

    @ApiModelProperty("主图地址")
    @Field
    private String mainImage;

    @ApiModelProperty("副图地址")
    @Field
    private String subImages;

    @ApiModelProperty("产品详情")
    @Field
    private String detail;

    @ApiModelProperty("价格")
    @Field
    private BigDecimal price;

    @ApiModelProperty("库存")
    @Field
    private Integer stock;

    @ApiModelProperty("状态")
    @Field
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("销售量")
    @Field
    private Integer sold;

}
