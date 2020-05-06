package com.giovanny.study.design.strategy;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.po.Uuu;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @packageName: com.giovanny.study.design.strategy
 * @className: TestStrategyAdapter
 * @description:
 * @author: YangJun
 * @date: 2020/5/6 11:37
 * @version: v1.0
 **/
//@Component
@Slf4j
public class TestStrategyAdapter extends ConvertStrategy {

    @RabbitListener(queues = "directQueue")
    public void test(String msg, Channel channel, Message message) {
        log.info("msg:[{}]", msg);
        Uuu uuu = JSONObject.parseObject(msg, Uuu.class);
        try {
            Object convert = convert(msg);
            log.info("转换。。。业务操作后,convert:[{}]", convert);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (ClassNotFoundException e) {
            log.error("没有找到类，ex:{},cause:{}", e.getMessage(), e.getCause());
        } catch (IOException e) {
            log.info("消费消息ack失败,ex:{}", e.getMessage());
        }
    }
}
