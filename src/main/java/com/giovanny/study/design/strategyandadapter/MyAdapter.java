package com.giovanny.study.design.strategyandadapter;

import org.springframework.stereotype.Component;

/**
 * @packageName: com.giovanny.study.design.strategyandadapter
 * @className: MyAdapter
 * @description: 适配器 策略  接口。提供功能
 * @author: YangJun
 * @date: 2020/5/6 16:55
 * @version: v1.0
 **/

public interface MyAdapter {
    /**
     * 业务方法
     *
     * @param json json String
     */
    void doMyWork(String json);

    /**
     * 返回使用哪个策略
     *
     * @return 接口实现类自行实现这个匹配规则
     */
    String getStrategy();
}
