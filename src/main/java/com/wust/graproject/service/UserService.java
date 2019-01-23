package com.wust.graproject.service;

import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserService
 * @Description 用户接口
 * @Author leis
 * @Date 2019/1/22 18:04
 * @Version 1.0
 **/
public interface UserService {

    /**
     * 登录
     * @param user
     * @param response
     * @return
     */
    ResultDataDto login(User user, HttpServletResponse response);
    /**
     * 注册用户
     * @param user
     * @return
     */
    ResultDataDto register(User user);

    /**
     * 校验用户名
     * @param str
     * @param type
     * @return
     */
    ResultDataDto checkValid(String str,String type);

    /**
     * 注销
     * @param request
     * @return
     */
    ResultDataDto logout(HttpServletRequest request,HttpServletResponse response);
}
