package com.wust.graproject.controller;

import com.wust.graproject.global.ResultDataDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ProduceController
 * @Description TODO
 * @Author leis
 * @Date 2019/2/20 10:36
 * @Version 1.0
 **/
@RestController
@RequestMapping("/product")
public class ProductController {

    @PostMapping(path = "/index")
    public ResultDataDto getIndex(){
        return null;
    }

}
