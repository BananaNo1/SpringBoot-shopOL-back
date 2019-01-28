package com.wust.graproject.service.impl;

import com.wust.graproject.common.Const;
import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.mapper.UserMapper;
import com.wust.graproject.service.UserService;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import com.wust.graproject.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 18:05
 * @Version 1.0
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public ResultDataDto login(User user, HttpServletResponse response) {
        if (user == null) {
            return ResultDataDto.operationErrorByMessage("参数为空");
        }
        User user1 = userMapper.selectByUsername(user.getUsername());
        if (user1 == null) {
            return ResultDataDto.operationErrorByMessage("用户名错误");
        }
        String pass = DigestUtils.md5DigestAsHex((user.getPassword() + Const.SALT + user1.getSalt()).getBytes());
        if (!user1.getPassword().equals(pass)) {
            return ResultDataDto.operationErrorByMessage("密码错误");
        }
        // redisTemplate.opsForValue().set(RedisPrefixKeyUtil.USER_LOGIN + user1.getId(), user1);
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        addToken(response, user1, token);

        return ResultDataDto.operationSuccessByMessage("登录成功");
    }

    private void addToken(HttpServletResponse response, User user, String token) {
        redisTemplate.opsForValue().set(RedisPrefixKeyUtil.TOKEN + token, user, 60 * 30, TimeUnit.SECONDS);
        Cookie cookie = new Cookie(Const.COOKIE_NAME_TOKEN, token);
        cookie.setDomain(Const.COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 30);
        response.addCookie(cookie);
    }

    @Override
    public ResultDataDto register(User user) {
        String verify = (String) redisTemplate.opsForValue().get(RedisPrefixKeyUtil.EMAIL_KEY + user.getEmail());
//        if (!user.getVerify().equals(verify)) {
//            return ResultDataDto.operationErrorByMessage("验证码错误");
//        }
//        ResultDataDto resultDataDto = checkValid(user.getUsername(), Const.USERNAME);
//        if (!resultDataDto.isSuccess()) {
//            return ResultDataDto.operationErrorByMessage("用户名已存在");
//        }
        String userSalt = UUID.randomUUID().toString().replaceAll("-", "");
        user.setSalt(userSalt);
        String pass = user.getPassword() + Const.SALT + userSalt;
        user.setPassword(DigestUtils.md5DigestAsHex(pass.getBytes()));
        user.setRole(1);
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return ResultDataDto.operationSuccess();
        }
        return ResultDataDto.operationError();
    }

    @Override
    public ResultDataDto checkValid(String str, String type) {
        if (Const.USERNAME.equals(type)) {
            Integer checkUsername = userMapper.checkUsername(str);
            if (checkUsername > 0) {
                return ResultDataDto.operationErrorByMessage("用户名已存在");
            }
        }
        if (Const.EMAIL.equals(type)) {
            User user = userMapper.selectByEmail(str);
            if (user == null) {
                return ResultDataDto.operationErrorByMessage("邮箱不存在");
            }
        }
        return ResultDataDto.operationSuccessByMessage("校验成功");
    }


    @Override
    public ResultDataDto logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (StringUtils.equals(cookie.getName(), Const.COOKIE_NAME_TOKEN)) {
                    cookie.setDomain(Const.COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return ResultDataDto.operationSuccess();
    }

    @Override
    public ResultDataDto resetPass(User user) {
        if(StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getEmail())||StringUtils.isEmpty(user.getVerify())){
            return ResultDataDto.operationErrorByMessage("参数为空");
        }
        String verifyCode = (String) redisTemplate.opsForValue().get(RedisPrefixKeyUtil.EMAIL_KEY + user.getEmail());
        if(!StringUtils.equals(verifyCode,user.getVerify())){
            return ResultDataDto.operationErrorByMessage("验证码错误");
        }
        User user1 = userMapper.selectByEmail(user.getEmail());
        String newPa= DigestUtils.md5DigestAsHex((user.getPassword()+Const.SALT+user1.getSalt()).getBytes());
        user1.setPassword(newPa);
        int update = userMapper.updateByPrimaryKeySelective(user1);
        if(update <= 0){
            return ResultDataDto.operationErrorByMessage("重置密码失败");
        }
        return ResultDataDto.operationSuccessByMessage("重置密码成功");
    }

    @Override
    public ResultDataDto checkVerify(HttpServletResponse response,String email, String verify) {
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(verify)){
                return ResultDataDto.operationErrorByMessage("参数为空");
        }
        if(!ValidatorUtil.isEmail(email)){
            return ResultDataDto.operationErrorByMessage("邮箱格式不正确");
        }
        String verifyCode = (String) redisTemplate.opsForValue().get(RedisPrefixKeyUtil.EMAIL_KEY + email);
        if(!StringUtils.equals(verify,verifyCode)){
            return  ResultDataDto.operationErrorByMessage("验证码不正确");
        }
        return ResultDataDto.operationSuccess("校验成功",email);
    }

    private void addCookie(HttpServletResponse response,String verify, String token){
        redisTemplate.opsForValue().set(RedisPrefixKeyUtil.REGISTER+verify,token,300,TimeUnit.SECONDS);
        Cookie cookie = new Cookie(Const.COOKIE_NAME_REGISTER,verify);
        cookie.setDomain(Const.COOKIE_DOMAIN);
        cookie.setMaxAge(300);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
