package com.wust.graproject.rabbitmq;

import com.wust.graproject.common.Const;
import com.wust.graproject.entity.Order;
import com.wust.graproject.entity.PayInfo;
import com.wust.graproject.entity.dto.MqDto;
import com.wust.graproject.mapper.PayInfoMapper;
import com.wust.graproject.util.MqKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leis
 * @Descirption
 * @date 2019/4/7 21:09
 */

@Service
@Slf4j
public class AlipayCallBackConsumer {

    @Autowired
    private PayInfoMapper payInfoMapper;

    @RabbitListener(queues = MqKeyUtil.QUEUE_ALIPAY_CALLBACK)
    public void receive(MqDto order) {
        log.info("付款的订单为：" + order.getId());
        PayInfo payInfo = new PayInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        payInfo.setPayPlatform(Const.PayPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(order.getPlatformNumber());
        payInfo.setPlatformStatus(order.getPlatformStatus());
        payInfoMapper.insert(payInfo);
    }
}
