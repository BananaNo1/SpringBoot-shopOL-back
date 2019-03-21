package com.wust.graproject.common;

/**
 * @ClassName Const
 * @Description TODO
 * @Author leis
 * @Date 2019/1/23 10:11
 * @Version 1.0
 **/
public class Const {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String SALT = "ajdijak@/";

    public static final String COOKIE_NAME_TOKEN = "token";
    public static final String COOKIE_NAME_REGISTER = "register";

    public static final String COOKIE_DOMAIN = "51wustzds.com";


    public interface Cart {
        int CHECKED = 1;
        int UN_CHECKED = 0;
        String LIMIT_NUM_FAIL = "LIMIT__NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }
}
