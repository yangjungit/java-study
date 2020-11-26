package com.giovanny.study.config.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @packageName: com.giovanny.study.config.redis
 * @className: FastJsonRedisSerializer
 * @description: 暂时没有用
 * @author: YangJun
 * @date: 2020/4/23 15:36
 * @version: v1.0
 **/
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz){
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return Optional.ofNullable(t)
                .map(r -> JSON.toJSONString(r, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET))
                .orElseGet(() -> new byte[0]);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return Optional.ofNullable(bytes)
                .map(t -> JSON.parseObject(new String(t, DEFAULT_CHARSET), clazz))
                .orElse(null);
    }

}

