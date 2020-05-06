package com.giovanny.study.design.strategyandadapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @packageName: com.giovanny.study.design.strategyandadapter
 * @className: MyAdapterImpl
 * @description:
 * @author: YangJun
 * @date: 2020/5/6 17:43
 * @version: v1.0
 **/
@Slf4j
@Component
public class MyAdapterImpl implements MyAdapter {
    @Override
    public void doMyWork(String json) {
        log.info("----------do my work!");
    }

    @Override
    public String getStrategy() {
        return "name-";
    }
}
