package com.wust.graproject.controller;

import com.wust.graproject.annotation.NeedLogin;
import com.wust.graproject.common.UserContext;
import com.wust.graproject.entity.User;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.service.UserService;
import com.wust.graproject.service.VerifyCodeService;
import com.wust.graproject.util.RedisPrefixKeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/user")
@Api("用户操作")
public class LoginController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private UserContext userContext;

    @PostMapping("/login")
    public ResultDataDto login(User user, Integer verifyCode, HttpServletRequest request, HttpServletResponse response) {
        if (user == null) {
            return ResultDataDto.operationErrorByMessage("参数为空");
        }
        boolean check = verifyCodeService.checkVerifyCode(verifyCode, (String) request.getSession().getAttribute("time"));
        if (!check) {
            return ResultDataDto.operationErrorByMessage("验证码不正确");
        }
        return userService.login(user, response);
    }

    @GetMapping("/verifyCode/{time}")
    @ApiOperation(value = "获取图形验证码")
    public ResultDataDto getVerifyCode(HttpServletResponse response, HttpSession session,
                                       @PathVariable(required = false) String time) {
        OutputStream outputStream = null;
        try {
            session.setAttribute("time", time);
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
    @ApiOperation("用户注册")
    public ResultDataDto register(@Valid User user) {
        return userService.register(user);
    }

    @PostMapping("/checkValid")
    @ApiOperation("校验")
    public ResultDataDto checkValid(String str, String type) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(type)) {
            return ResultDataDto.operationErrorByMessage("参数不能为空");
        }
        return userService.checkValid(str, type);
    }

    @PostMapping("/checkVerify")
    @ApiOperation("检验邮箱验证码")
    public ResultDataDto checkVerify(HttpServletResponse response, String email, String verify) {
        return userService.checkVerify(response, email, verify);
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    public ResultDataDto resetPass(User user) {
        return userService.resetPass(user);
    }

    @PostMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    public ResultDataDto getUserInfo() {
        if (userContext.getUser() != null) {
            userContext.getUser().setPassword("");
            userContext.getUser().setSalt("");
            userContext.getUser().setId(null);
            return ResultDataDto.operationSuccess(userContext.getUser());
        }
        return ResultDataDto.operationErrorByMessage("未登录");
    }


    @PostMapping("/logout")
    @ApiOperation("注销用户")
    public ResultDataDto logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }

    @PostMapping("/getUserInformation")
    @NeedLogin
    public ResultDataDto getUserInformation() {
        return userService.getUserInformation();
    }


    @PostMapping("/updateInformation")
    @NeedLogin
    public ResultDataDto updateInformation(HttpServletRequest request, User user) {
        return userService.updateInformation(request, user);
    }

    @PostMapping("/updatePassword")
    @NeedLogin
    public ResultDataDto updatePassword(String passwordOld, String passwordNew) {
        return userService.updatePassword(passwordOld, passwordNew);
    }

}
