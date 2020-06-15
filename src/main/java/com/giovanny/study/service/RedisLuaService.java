package com.giovanny.study.service;

/**
 * @packageName: com.example.demo1.service
 * @className: RedisLuaService
 * @description:
 * @author: YangJun
 * @date: 2020/4/23 16:15
 * @version: v1.0
 **/
public interface RedisLuaService {
    /**
     * 限流
     *
     * @param key       redis key
     * @param perSecNum 限制每秒多少次
     */
    void limitFlow(String key, String perSecNum);
}
