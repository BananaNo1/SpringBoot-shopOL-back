package com.wust.graproject.entity.email;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName EmailModel
 * @Description TODO
 * @Author leis
 * @Date 2019/1/18 13:51
 * @Version 1.0
 **/
@Data
public class EmailModel implements Serializable {

    private String recipient;
    private String subject;
    private String content;
}
