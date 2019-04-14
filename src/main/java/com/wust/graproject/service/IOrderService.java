package com.wust.graproject.service;

import com.wust.graproject.global.ResultDataDto;

import java.util.Map;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/18 22:11
 */

public interface IOrderService {
    /**
     * 创建订单
     *
     * @param shippingId
     * @return
     */
    ResultDataDto create(Integer shippingId);

    /**
     * 获取订单中的产品
     *
     * @return
     */
    ResultDataDto getOrderCartProduct();

    /**
     * 获取订单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultDataDto getOrderList(int pageNum, int pageSize);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    ResultDataDto detail(Long orderNo);

    /**
     * 取消订单
     *
     * @param orderNo
     * @return
     */
    ResultDataDto cancelOrder(Long orderNo);

    /**
     * 支付接口
     *
     * @param orderNo
     * @return
     */
    ResultDataDto pay(Long orderNo);

    /**
     * 支付宝回调
     *
     * @param params
     * @return
     */
    ResultDataDto alipayCallBack(Map<String, String> params);

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    ResultDataDto queryOrderPayStatus(Long orderNo);
}
