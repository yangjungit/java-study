package com.giovanny.study.design.strategy;

import org.springframework.stereotype.Component;

/**
 * @packageName: com.giovanny.study.design.Strategy
 * @className: RabbitMsgConcert
 * @description:
 * @author: YangJun
 * @date: 2020/5/6 9:56
 * @version: v1.0
 **/
@Component
public interface RabbitMsgConcert {
    /**
     * 将消息转换成目标对象
     *
     * @param msg json
     * @return 返回目标对象
     */
    Object convert(String msg);

    String name();
}
