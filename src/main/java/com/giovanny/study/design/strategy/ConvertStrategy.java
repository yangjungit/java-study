package com.giovanny.study.design.strategy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.User;
import com.giovanny.study.entity.po.Uuu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @packageName: com.giovanny.study.design.strategy
 * @className: ConvertStrategy
 * @description:
 * @author: YangJun
 * @date: 2020/5/6 10:17
 * @version: v1.0
 **/
public class ConvertStrategy {
    @Value("${convert.rabbitmq.user}")
    private String userClassName;

    @Value("${convert.rabbitmq.uuu}")
    private String uuuClassName;

    @Autowired
    private List<RabbitMsgConcert> concerts;

    public Object convert(String msg) throws ClassNotFoundException {

        concerts.forEach(c -> {
            Uuu uuu = JSONObject.parseObject(msg, Uuu.class);
            if (c.name().equals(uuu.getName())) {
                c.convert(msg);
            }
        });
        RabbitMsgConcert concert;

        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class cl = Uuu.class;
        String name = cl.getName();
        Class<?> aClass = Class.forName(name);

        Uuu uuu = new Uuu();
        Class<? extends Uuu> aClass1 = uuu.getClass();

        System.out.println(cl == aClass);
        System.out.println(cl == aClass1);
        System.out.println("name=" + name);
    }
}
