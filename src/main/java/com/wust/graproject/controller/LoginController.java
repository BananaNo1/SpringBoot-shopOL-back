package com.wust.graproject.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import static com.wust.graproject.util.VerifyCode.createVerifyCode;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author leis
 * @Date 2019/1/4 14:27
 * @Version 1.0
 **/

@RestController
public class LoginController {

    @RequestMapping("/verifyCode/{time}")
    public void getVerifyCode(HttpServletResponse response,@PathVariable(required = false) String time) {
            OutputStream outputStream = null;
        try {
            BufferedImage image = createVerifyCode();
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
    }

}
