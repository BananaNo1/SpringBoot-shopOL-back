package com.wust.graproject.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author aisino
 */
@Data
public class Clothes {
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