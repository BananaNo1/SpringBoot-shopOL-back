package com.wust.graproject.rabbitmq;

import com.wust.graproject.util.MqKeyUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author leis
 * @Descirption
 * @date 2019/4/7 15:14
 */
@Configuration
public class ExchangeQueue {

    @Bean
    public DirectExchange directExchangeAlipayCallBack() {
        return new DirectExchange(MqKeyUtil.DIRECT_EXCHANGE_ALIPAY_CALLBACK);
    }

    @Bean
    public Queue queueAlipayCallBack() {
        return new Queue(MqKeyUtil.QUEUE_ALIPAY_CALLBACK, true);
    }

    @Bean
    public Binding bindingAlipayCallBack() {
        return BindingBuilder.bind(queueAlipayCallBack()).
                to(directExchangeAlipayCallBack()).with(MqKeyUtil.ROUTING_KEY_ALIPAY_CALLBACK);
    }
}
