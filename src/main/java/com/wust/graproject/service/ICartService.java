package com.wust.graproject.service;

import com.wust.graproject.global.ResultDataDto;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/11 10:33
 */

public interface ICartService {

    /**
     * 获取购物车列表
     *
     * @return
     */
    ResultDataDto list();

    /**
     * 获取购物车数量
     *
     * @return
     */
    ResultDataDto getCartProductCount();


    /**
     * 添加购物车
     *
     * @param count
     * @param productId
     * @return
     */
    ResultDataDto addToCart(Integer count, Integer productId);

    /**
     * 更新购物车
     *
     * @param count
     * @param productId
     * @return
     */
    ResultDataDto updateProduct(Integer count, Integer productId);

    /**
     * 删除购物车商品
     *
     * @param productIds
     * @return
     */
    ResultDataDto deleteProduct(String productIds);

    /**
     * 选择或者取消选择商品
     *
     * @param productId
     * @param checked
     * @return
     */
    ResultDataDto selectOrUnSelect(Integer productId,Integer checked);
}
