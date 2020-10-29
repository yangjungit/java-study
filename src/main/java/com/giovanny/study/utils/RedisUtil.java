package com.giovanny.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @packageName: com.giovanny.study.utils
 * @className: RedisUtil
 * @description: RedisUtil  不要将这个里面的异常捕获了，后面可以写一个切面来处理异常，如redis重连
 * @author: YangJun
 * @date: 2020/4/24 16:58
 * @version: v1.0
 **/
@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 给某坨代码A加锁，返回是否持有这坨代码的锁
     * 虽然已经判断了当前时间了，但还可以扩展一下，设置锁得过期时间（存在时间） timeout 可以设置的稍长一点
     * 有这个到期时间后就不需要后续if的判断了
     *
     * @param key        令牌
     * @param newExpires 持有锁的到期时间（这里用E来表示） 注意是到期时间，前面传参时已经加了当前时间了
     * @return 是否持有锁
     */
    public boolean scheduleLock(String key, String newExpires) {
        //如果key不存在的话设置k-v,存在的话不设置
        Boolean isAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, newExpires);
        if (isAbsent != null) {
            //key不存在,设置后返回true，先访问A的线程就可以进来并锁上门不让其它线程进入
            if (isAbsent) {
                return true;
            }
            //key存在，获取当前E
            String currentE = stringRedisTemplate.opsForValue().get(key);

            //如果E不为空且过期
            if (!StringUtils.isEmpty(currentE)
                    && Long.parseLong(currentE) < System.currentTimeMillis()) {
                //有可能两个及以上的线程同时到了这一步，但总有一个会捷足先登
                //先到的再获取一次E并设置新的E,返回true持有锁，其它的线程接着执行oldE.equals(currentE)
                //是false最终拒绝进入A
                String oldE = stringRedisTemplate.opsForValue().getAndSet(key, newExpires);
                return !StringUtils.isEmpty(oldE) && oldE.equals(currentE);
            }
            //如果锁没有过期就返回false，不让其它线程进来
            return false;
        }
        throw new RuntimeException("redis lock 异常");
    }

    /**
     * @param script 脚本
     * @param keys   key
     * @param args   参数
     * @return 获取token成功与否 ，true成功 。即是否可以执行业务
     * @throws Exception 异常
     */
    public Boolean acquire(RedisScript<Boolean> script, List<String> keys, Object... args) throws Exception {
        Boolean aBoolean = stringRedisTemplate.execute(script, keys, args);
        if (aBoolean != null) {
            return aBoolean;
        }
        log.warn("stringRedisTemplate.execute return null !");
        return false;
    }

}
