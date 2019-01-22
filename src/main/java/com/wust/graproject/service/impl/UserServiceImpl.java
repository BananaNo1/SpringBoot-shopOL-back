package com.wust.graproject.service.impl;

import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.mapper.UserMapper;
import com.wust.graproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 18:05
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultDataDto register(User user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setRole(1);
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return ResultDataDto.operationSuccess();
        }
        return ResultDataDto.operationError();
    }
}
