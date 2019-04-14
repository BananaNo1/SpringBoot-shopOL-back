package com.wust.graproject.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.wust.graproject.annotation.NeedLogin;
import com.wust.graproject.common.Const;
import com.wust.graproject.global.ResultDataDto;
import com.wust.graproject.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/18 21:44
 */

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/create")
    @NeedLogin
    public ResultDataDto create(Integer shippingId) {
        return orderService.create(shippingId);
    }

    @RequestMapping("/getOrderCartProduct")
    @NeedLogin
    public ResultDataDto getOrderCartProduct() {
        return orderService.getOrderCartProduct();
    }

    @RequestMapping("/list")
    @NeedLogin
    public ResultDataDto list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return orderService.getOrderList(pageNum, pageSize);
    }

    @RequestMapping("/detail")
    @NeedLogin
    public ResultDataDto detail(Long orderNo) {
        return orderService.detail(orderNo);
    }

    @RequestMapping("/cancelOrder")
    @NeedLogin
    public ResultDataDto cancel(Long orderNo) {
        return orderService.cancelOrder(orderNo);
    }

    @RequestMapping("/pay")
    @NeedLogin
    public ResultDataDto pay(Long orderNo) {
        return orderService.pay(orderNo);
    }

    @RequestMapping("/alipayCallBack")
    public Object alipayCallBack(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map requestParameterMap = request.getParameterMap();
        for (Iterator iterator = requestParameterMap.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParameterMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        log.info("支付宝回调,sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());
        params.remove("sign_type");
        try {
            boolean checkV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!checkV2) {
                return ResultDataDto.operationErrorByMessage("非法请求");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝回调异常", e);
        }
        ResultDataDto resultDataDto = orderService.alipayCallBack(params);
        if (resultDataDto.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }


    @RequestMapping("/queryOrderPayStatus")
    @NeedLogin
    public ResultDataDto queryOrderPayStatus(Long orderNo) {
        ResultDataDto resultDataDto = orderService.queryOrderPayStatus(orderNo);
        if (resultDataDto.isSuccess()) {
            return ResultDataDto.operationSuccess(true);
        }
        return ResultDataDto.operationSuccess(false);
    }
}
