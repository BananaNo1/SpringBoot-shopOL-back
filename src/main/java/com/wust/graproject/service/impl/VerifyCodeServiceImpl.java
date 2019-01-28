package com.wust.graproject.service.impl;

import com.wust.graproject.service.VerifyCodeService;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName VerifyCodeServiceImpl
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 17:48
 * @Version 1.0
 **/
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private static final String COOKIE_NAME_VERIF= "vterifiyme";

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @Override
    public BufferedImage createVerifyCode(String time) {
        int width = 81;
        int height = 38;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(new Color(0xDCDCDC));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, width - 1, height - 1);
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.drawOval(x, y, 0, 0);
        }
        String verifyCode = generateVerifyCode(random);
        graphics.setColor(new Color(0, 100, 0));
        graphics.setFont(new Font("Candara", Font.BOLD, 24));
        graphics.drawString(verifyCode, 8, 24);
        graphics.dispose();
        int calculate = calculate(verifyCode);
        redisTemplate.opsForValue().set(RedisPrefixKeyUtil.VERIFY_CODE_KEY + time, calculate, 10, TimeUnit.MINUTES);
        return bufferedImage;
    }

    private void addCookie(HttpServletResponse  response,String token){
        Cookie cookie =new Cookie(COOKIE_NAME_VERIF,token);
        cookie.setMaxAge(60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public boolean checkVerifyCode(Integer verifyCode, String time) {
        Integer cal = redisTemplate.opsForValue().get(RedisPrefixKeyUtil.VERIFY_CODE_KEY + time);
        if (cal == null || cal - verifyCode != 0) {
            return false;
        }
       // redisTemplate.delete(RedisPrefixKeyUtil.VERIFY_CODE_KEY + time);
        return true;
    }


    private int calculate(String verifyCode) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            return (int) engine.eval(verifyCode);
        } catch (ScriptException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private char[] opr = new char[]{'+', '-', '*'};

    private String generateVerifyCode(Random random) {
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        char opr1 = opr[random.nextInt(3)];
        char opr2 = opr[random.nextInt(3)];
        String exp = "" + num1 + opr1 + num2 + opr2 + num3;
        return exp;
    }
}
