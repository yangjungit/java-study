package com.giovanny.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
     *
     * @param key        令牌
     * @param newExpires 持有锁的到期时间（这里用E来表示）
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
        throw new RuntimeException("redis lock 错误");
    }

}
