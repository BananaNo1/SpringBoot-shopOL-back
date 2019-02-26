package com.wust.graproject.config.mq;

import com.wust.graproject.util.MqKeyUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RabbitMqConfig
 * @Description TODO
 * @Author leis
 * @Date 2019/2/26 10:32
 * @Version 1.0
 **/
@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;


    @Bean
    public Executor getExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                15,
                20,
                60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());
        return executor;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        connectionFactory.setExecutor(getExecutor());
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange directExchangeES() {
        return new DirectExchange(MqKeyUtil.DIRECT_EXCHANGE_ES);
    }

    @Bean
    public Queue queueES() {
        return new Queue(MqKeyUtil.ROUTING_KEY_ES, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueES()).to(directExchangeES()).with(MqKeyUtil.QUEUE_ES);
    }


}
