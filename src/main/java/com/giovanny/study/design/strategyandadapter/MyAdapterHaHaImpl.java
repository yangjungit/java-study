package com.giovanny.study.design.strategyandadapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @packageName: com.giovanny.study.design.strategyandadapter
 * @className: MyAdapterHaHaImpl
 * @description: //TODO
 * @author: YangJun
 * @date: 2020/5/6 17:59
 * @version: v1.0
 **/
@Component
@Slf4j
public class MyAdapterHaHaImpl implements MyAdapter {
    @Override
    public void doMyWork(String json) {
        log.info("haha do my work...");
    }

    @Override
    public String getStrategy() {
        return "haha";
    }
}
