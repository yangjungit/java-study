package com.giovanny.study.service.impl;

import com.giovanny.study.service.RedisLuaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @packageName: com.example.demo1.service.impl
 * @className: RedisLuaServiceImpl
 * @description: 限流业务
 * @author: YangJun
 * @date: 2020/4/23 16:15
 * @version: v1.0
 **/
@Slf4j
@Service
public class RedisLuaServiceImpl implements RedisLuaService {

    /**
     * stringRedisTemplate  本地配置的RedisTemplate value序列化方式是正对Object 序列化成json
     * 所以用stringRedisTemplate序列化成string
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "limitFlowScript")
    private RedisScript<Boolean> redisScript;

    @Override
    public void limitFlow(String key, String perSecNum) {
        Boolean flag = stringRedisTemplate.execute(redisScript, Arrays.asList(key, "test"), perSecNum, "1");
        log.info("param :[key:{}，perSecNum:{}]", key, perSecNum);
        log.info("lua return :[{}]", flag);
        if (flag != null && flag) {
            log.info("能访问 :[{}]", flag);
        } else {
            log.info("不能访问 :[{}]", flag);
        }


    }
}
