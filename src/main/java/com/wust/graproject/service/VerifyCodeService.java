package com.wust.graproject.service;

import java.awt.image.BufferedImage;

/**
 * @ClassName VerifyCodeService
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 17:47
 * @Version 1.0
 **/
public interface VerifyCodeService {
    /**
     * 创建验证码
     * @param time
     * @return
     */
    BufferedImage createVerifyCode(String time);
}
