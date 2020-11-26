package com.giovanny.study.design.strategy;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.User;
import com.giovanny.study.entity.po.Uuu;

/**
 * @packageName: com.giovanny.study.design.Strategy
 * @className: RabbitMsgConcertToUserImpl
 * @description:
 * @author: YangJun
 * @date: 2020/5/6 10:12
 * @version: v1.0
 **/
public class RabbitMsgConcertToUserImpl implements RabbitMsgConcert {
    @Override
    public Object convert(String msg) {
        User object = (User) JSONObject.parseObject(msg, User.class);
        return object;
    }

    @Override
    public String name() {
        return null;
    }
}
