package com.wust.graproject.service;

import com.wust.graproject.entity.Shipping;
import com.wust.graproject.global.ResultDataDto;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/18 21:41
 */

public interface IShippingService {
    /**
     * 获取购物车列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultDataDto list(int pageNum, int pageSize);

    /**
     * 选择地址
     *
     * @param shippingId
     * @return
     */
    ResultDataDto select(Integer shippingId);

    /**
     * 添加收货地址
     *
     * @param shipping
     * @return
     */
    ResultDataDto add(Shipping shipping);

    /**
     * 更新当前登录用户的所选择的地址
     *
     * @param shipping
     * @return
     */
    ResultDataDto update(Shipping shipping);

    /**
     * 删除选中地址
     *
     * @param shippingId
     * @return
     */
    ResultDataDto delete(Integer shippingId);
}
