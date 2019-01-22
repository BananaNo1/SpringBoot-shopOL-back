package com.wust.graproject.entity;

import com.wust.graproject.annotation.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author aisino
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    private String username;

    private String password;

    private String email;

    @IsMobile
    private String phone;

    private Integer role;

    private Date createTime;

    private Date updateTime;


}