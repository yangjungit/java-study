package com.giovanny.study.controller;


import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.service.RedisLuaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName: com.example.demo1.controller
 * @className: RedisLuaController
 * @description:
 * @author: YangJun
 * @date: 2020/4/23 16:13
 * @version: v1.0
 **/
@RestController
public class RedisLuaController {

    @Autowired
    private RedisLuaService redisLuaService;

    @RequestMapping(path = "/redis/lua/limit", method = RequestMethod.GET)
    public MyResponse redisLuaFlow(String key, String perSecNum) {
        redisLuaService.limitFlow(key, perSecNum);
        return MyResponse.success();
    }
}
