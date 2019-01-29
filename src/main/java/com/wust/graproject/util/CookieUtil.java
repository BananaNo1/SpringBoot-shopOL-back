package com.wust.graproject.util;

import com.wust.graproject.common.Const;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CookieUtil
 * @Description TODO
 * @Author leis
 * @Date 2019/1/29 16:50
 * @Version 1.0
 **/
public class CookieUtil {

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (StringUtils.isEmpty(cookieName) || cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    public static String deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (StringUtils.equals(cookie.getName(), cookieName)) {
                    cookie.setDomain(Const.COOKIE_DOMAIN);
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}
