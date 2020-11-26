package com.giovanny.study.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @packageName: com.giovanny.study.rabbitmq
 * @className: RabbitMsgProducer
 * @description: RabbitMsgProducer
 * @author: YangJun
 * @date: 2020/4/24 17:36
 * @version: v1.0
 **/
@Component
public class RabbitMsgProducer {
    final
    RabbitTemplate rabbitTemplate;

    public RabbitMsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public  void convertAndSend(String routingKey, Object msg) {
        rabbitTemplate.convertAndSend(routingKey, JSONObject.toJSONString(msg));
    }

}
