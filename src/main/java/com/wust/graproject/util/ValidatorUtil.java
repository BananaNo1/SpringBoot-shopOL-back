package com.wust.graproject.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName ValidatorUtil
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 18:34
 * @Version 1.0
 **/
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    private static final Pattern email_pattern = Pattern.compile("(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})");

    public static boolean isMobile(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(src);
        return matcher.matches();
    }


    public static boolean isEmail(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher matcher = email_pattern.matcher(src);
        return matcher.matches();
    }

}
