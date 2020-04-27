package com.giovanny.study.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.User;
import com.giovanny.study.entity.po.Uuu;
import com.giovanny.study.service.UserService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * @packageName: com.example.demo1.rabbitmq
 * @className: RabbitReceiver
 * @description: rabbit 消费者
 * @author: YangJun
 * @date: 2020/4/13 14:48
 * @version: v1.0
 **/
@Component
@Slf4j
public class RabbitReceiver {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("executor")
    private Executor executor;

    @RabbitListener(queues = "directQueue")
    public void helloReceiverChannel(String msg, Channel channel, Message message) {
//        log.info("rabbit mq msg:[{}],channel:[{}],message:[{}]", "msg", channel, message);
        /*
          使用rabbitTemplate.convertAndSend时：
          SimpleRabbitListenerContainerFactory 手动配置了setMessageConverter(new Jackson2JsonMessageConverter());
          因此消息（String msg）就应该是json格式的，不然会因为转换不了而报错 但这种方式会比较快

          也可以用默认的转换，即不设置setMessageConverter  方法上用 string 接收[helloReceiver(String msg, Channel channel, Message message)]，然后自己手动转换如下代码
          -----
          使用rabbitTemplate.send时：
          就不要设置setMessageConverter了，接收String msg 就是json格式的，Message里面的还是byte[],也可以用下面的方式转换
          -----
          当直接发送对象过来时，没有测试过。懵逼。实际开发中还是json好用，容错性更好
         */
        executor.execute(() -> {
            try {
                String body = new String(message.getBody());
//            log.info("body:[{}]", body);
            /*
              用更简单的转换方式
              <pre>User user1 = JSONObject.toJavaObject(JSONObject.parseObject(body), User.class);
              log.info("user1:[{}]", user1);
             */
                Uuu uuu = JSONObject.parseObject(msg, Uuu.class);
                userService.insert(uuu);
                if ("name-0-0".equals(uuu.getName()) || "name-9-199".equals(uuu.getName())) {
                    log.info("uuu:[{}，{}]", uuu,System.currentTimeMillis());
                }
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                log.error("exception message:{},cause:{}", e.getMessage(), e.getCause());
            }
        });


    }
}
