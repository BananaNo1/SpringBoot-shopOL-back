package com.wust.graproject.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 *@Description 属性参照product(com.wust.graproject.entity.Product)类
 *@Author leis
 *@Date  2019/2/21 11:08
 **/
@Data
public class Television {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer sold;

}