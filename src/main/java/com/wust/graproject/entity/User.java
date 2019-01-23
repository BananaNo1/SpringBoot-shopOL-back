package com.wust.graproject.entity;

import com.wust.graproject.annotation.validator.IsMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author aisino
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel
public class User implements Serializable {

    private static final long serialVersionUID = 2806745255167295271L;

    @ApiModelProperty("用户主键id")
    private Integer id;

    @NotNull
    @ApiModelProperty("用户名")
    private String username;

    @NotNull
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("盐值")
    private String salt;

    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

    @IsMobile
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱验证码")
    @NotNull
    private String verify;

    @ApiModelProperty(value = "用户角色 1:普通 0:",example = "1")
    private Integer role;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}