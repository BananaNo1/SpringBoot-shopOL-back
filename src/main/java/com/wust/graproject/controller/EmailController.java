package com.wust.graproject.controller;

import com.wust.graproject.entity.email.EmailModel;
import com.wust.graproject.util.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName EmailController
 * @Description TODO
 * @Author leis
 * @Date 2019/1/18 14:06
 * @Version 1.0
 **/
@RestController
public class EmailController {

    @Autowired
    private EmailUtils emailUtils;

    @PostMapping("/sendEmail")
    public void send(String email) {
        EmailModel emailModel = new EmailModel();
        emailModel.setRecipient(email);
        emailUtils.sendEmail(emailModel);
    }

}
