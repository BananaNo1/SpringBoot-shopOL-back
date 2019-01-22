package com.wust.graproject.service;

import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;

/**
 * @ClassName UserService
 * @Description 用户接口
 * @Author leis
 * @Date 2019/1/22 18:04
 * @Version 1.0
 **/
public interface UserService {

    /**
     * 注册用户
     * @param user
     * @return
     */
    ResultDataDto register(User user);
}
