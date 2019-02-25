package com.wust.graproject.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author aisino
 */
@Data
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

}