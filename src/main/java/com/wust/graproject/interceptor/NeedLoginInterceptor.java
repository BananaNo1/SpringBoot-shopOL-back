package com.wust.graproject.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.wust.graproject.annotation.NeedLogin;
import com.wust.graproject.common.Const;
import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.util.CookieUtil;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName NeedLoginInterceptor
 * @Description TODO
 * @Author leis
 * @Date 2019/1/29 16:45
 * @Version 1.0
 **/
@Service
public class NeedLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NeedLogin methodAnnotation = handlerMethod.getMethodAnnotation(NeedLogin.class);
            if (methodAnnotation == null) {
                return true;
            }
            Cookie cookie = CookieUtil.getCookie(request, Const.COOKIE_NAME_TOKEN);
            String token = null;
            if (cookie != null) {
                token = cookie.getValue();
            }
            User user = (User) redisTemplate.opsForValue().get(RedisPrefixKeyUtil.TOKEN + token);
            if (cookie == null || token == null || user == null) {
                String need = JSONObject.toJSONString(ResultDataDto.operationNeedLogin());
                returnJson(response, need);
                return false;
            }
        }
        return true;
    }

    private void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");

        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
