package com.giovanny.study.design.strategyandadapter;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.po.Uuu;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @packageName: com.giovanny.study.design.strategyandadapter
 * @className: TestRabbitMsg
 * @description: 模拟开发场景
 * @author: YangJun
 * @date: 2020/5/6 17:32
 * @version: v1.0
 **/
@Component
@Slf4j
public class TestRabbitMsg extends MyStrategy {


    @RabbitListener(queues = "${spring.rabbitmq.direct-queue-name}")
    public void test(String msg, Channel channel, Message message) {
        log.info("msg:[{}]", msg);
        Uuu uuu = JSONObject.parseObject(msg, Uuu.class);
        try {
            super.workAdapter(msg, uuu.getName());
            log.info("业务操作后");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            log.info("消费消息ack失败,ex:{}", e.getMessage());
        }
    }
}
