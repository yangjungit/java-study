package com.giovanny.study.design.strategy;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.po.Uuu;

/**
 * @packageName: com.giovanny.study.design.Strategy
 * @className: RabbitMsgConcertToUuuImpl
 * @description: //TODO
 * @author: YangJun
 * @date: 2020/5/6 10:05
 * @version: v1.0
 **/
public class RabbitMsgConcertToUuuImpl implements RabbitMsgConcert {
    @Override
    public Object convert(String msg) {
        Uuu object = (Uuu) JSONObject.parseObject(msg, Uuu.class);
        return object;
    }

    @Override
    public String name() {

        return null;
    }
}
