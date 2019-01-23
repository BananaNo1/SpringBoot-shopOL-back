package com.wust.graproject.interceptor;

import com.wust.graproject.common.Const;
import com.wust.graproject.common.UserContext;
import com.wust.graproject.entity.User;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Description TODO
 * @Author leis
 * @Date 2019/1/23 17:00
 * @Version 1.0
 **/
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserContext userContext;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(Const.SALT)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null) {
            User user = (User) redisTemplate.opsForValue().get(RedisPrefixKeyUtil.TOKEN + token);
            if (user == null) {
                return true;
            }
            userContext.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userContext.clear();
    }
}



