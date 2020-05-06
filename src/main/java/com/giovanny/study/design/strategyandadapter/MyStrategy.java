package com.giovanny.study.design.strategyandadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @packageName: com.giovanny.study.design.strategyandadapter
 * @className: MyStrategy
 * @description: 匹配某个策略
 * @author: YangJun
 * @date: 2020/5/6 17:14
 * @version: v1.0
 **/

public class MyStrategy {
    /**
     * <>@Autowired 按照类型注入，注入接口时，若有多个实现类的时候会是一个实现类的列表，通常会和@Qualifier配合使用
     * required 为false时，找不到会报错
     */
    @Autowired
    private List<MyAdapter> myAdapters;

    public void workAdapter(String json, String strategyName) {
        myAdapters.forEach(ad -> {
            if (strategyName.equals(ad.getStrategy())) {
                ad.doMyWork(json);
            }
        });
    }
}
