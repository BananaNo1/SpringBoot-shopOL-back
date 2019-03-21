package com.wust.graproject.controller;

import com.wust.graproject.annotation.NeedLogin;
import com.wust.graproject.common.Const;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 10:32
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @NeedLogin
    @PostMapping("/list")
    public ResultDataDto list() {
        return cartService.list();
    }

    @PostMapping("/getCartProductCount")
    public ResultDataDto getCartProductCount() {
        return cartService.getCartProductCount();
    }

    @PostMapping("/addCart")
    @NeedLogin
    public ResultDataDto addToCart(Integer count, Integer productId) {
        return cartService.addToCart(count, productId);
    }

    @PostMapping("/updateProduct")
    @NeedLogin
    public ResultDataDto updateProduct(Integer count, Integer productId) {
        return cartService.updateProduct(count, productId);
    }

    @NeedLogin
    @PostMapping("/deleteProduct")
    public ResultDataDto deleteProduct(String productIds) {
        return cartService.deleteProduct(productIds);
    }

    @NeedLogin
    @PostMapping("/selectAll")
    public ResultDataDto selectAll() {
        return cartService.selectOrUnSelect(null, Const.Cart.CHECKED);
    }

    @NeedLogin
    @PostMapping("/unSelectAll")
    public ResultDataDto unSelectAll() {
        return cartService.selectOrUnSelect(null, Const.Cart.UN_CHECKED);
    }

    @NeedLogin
    @PostMapping("/select")
    public ResultDataDto select(Integer productId) {
        return cartService.selectOrUnSelect(productId, Const.Cart.CHECKED);
    }

    @NeedLogin
    @PostMapping("/unSelect")
    public ResultDataDto unSelect(Integer productId) {
        return cartService.selectOrUnSelect(productId, Const.Cart.UN_CHECKED);
    }


}
