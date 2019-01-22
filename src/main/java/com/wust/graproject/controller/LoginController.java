package com.wust.graproject.controller;

import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.service.VerifyCodeService;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;


/**
 * @ClassName LoginController
 * @Description 用户操作
 * @Author leis
 * @Date 2019/1/4 14:27
 * @Version 1.0
 **/

@RestController
public class LoginController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/verifyCode/{time}")
    public ResultDataDto getVerifyCode(HttpServletResponse response, @PathVariable(required = false) String time) {
            OutputStream outputStream = null;
        try {
            BufferedImage image = verifyCodeService.createVerifyCode(time);
            outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultDataDto.operationSuccess(redisTemplate.opsForValue().get(RedisPrefixKeyUtil.VERIFY_CODE_KEY));
    }

    @PostMapping("/register")
    public void register(@Valid User user){
            return ;
    }



}
