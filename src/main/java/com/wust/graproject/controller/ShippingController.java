package com.wust.graproject.controller;

import com.wust.graproject.annotation.NeedLogin;
import com.wust.graproject.entity.Shipping;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @author leis
 * @date 2019/3/18 21:40
 * @return
 */
@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping("/select")
    @NeedLogin
    public ResultDataDto select(Integer shippingId) {
        return shippingService.select(shippingId);
    }

    @PostMapping("/add")
    @NeedLogin
    public ResultDataDto add(Shipping shipping) {
        return shippingService.add(shipping);
    }

    @PostMapping("/update")
    @NeedLogin
    public ResultDataDto update(Shipping shipping) {
        return shippingService.update(shipping);
    }

    @PostMapping("delete")
    @NeedLogin
    public ResultDataDto delete(Integer shippingId) {
        return shippingService.delete(shippingId);
    }

    @PostMapping("/list")
    @NeedLogin
    public ResultDataDto list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return shippingService.list(pageNum, pageSize);
    }
}
