package com.giovanny.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName: com.example.demo1.controller
 * @className: RabbitMqController
 * @description: Rabbit mq 测试controller
 * @author: YangJun
 * @date: 2020/4/13 14:35
 * @version: v1.0
 **/
@Slf4j
@RestController
public class RabbitMqController {
    final
    RabbitTemplate rabbitTemplate;

    public RabbitMqController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping("/rabbit/hello")
    public String sendRabbitMqMsg() {
//        String msg = "hello rabbit";
        User user = new User("sd11", "h哈", "12.sd和");
        rabbitTemplate.convertAndSend("directQueue", JSONObject.toJSONString(user));
//        MessageProperties properties = new MessageProperties();
//        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
//        Message message = new Message(JSONObject.toJSONBytes(user),properties);
//        rabbitTemplate.send("directQueue",message);
        return "success";
    }
}
