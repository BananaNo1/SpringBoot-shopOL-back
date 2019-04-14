package com.wust.graproject.rabbitmq;

import com.wust.graproject.entity.Order;
import com.wust.graproject.entity.dto.MqDto;
import com.wust.graproject.util.MqKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author leis
 * @Descirption
 * @date 2019/4/7 15:20
 */

@Service
@Slf4j
public class AlipayCallBackProducer implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public AlipayCallBackProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(MqDto order) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(MqKeyUtil.DIRECT_EXCHANGE_ALIPAY_CALLBACK,
                MqKeyUtil.ROUTING_KEY_ALIPAY_CALLBACK,
                order, correlationId);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("回调id:" + correlationData);
        if (b) {
            log.info("消费成功");
        } else {
            log.info("消费失败" + s);
        }
    }
}
