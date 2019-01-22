package com.wust.graproject.util;

import com.wust.graproject.entity.email.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Random;

/**
 * @ClassName EmailUtils
 * @Description TODO
 * @Author leis
 * @Date 2019/1/18 13:52
 * @Version 1.0
 **/
@Service
public class EmailUtils {

    @Value("${lance.mail.sender}")
    private String MAIL_SENDER;

    @Autowired
    private JavaMailSenderImpl mailSender;

    private static final String SYMBOLS = "0123456789";
    private static final Random RANDOM = new SecureRandom();

    public void sendEmail(EmailModel emailModel) {
        MimeMessage message = null;
        try {
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(MAIL_SENDER);
            helper.setTo(emailModel.getRecipient());
            helper.setSubject("欢迎注册");
            String str = getStr();
            helper.setText("验证码为" + str + "(有效时间为60s,请勿告诉他人)", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getStr() {
        char[] chars = new char[6];
        for (int index = 0; index < chars.length; index++) {
            chars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(chars);
    }

}
