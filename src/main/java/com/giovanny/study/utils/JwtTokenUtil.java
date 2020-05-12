package com.giovanny.study.utils;

import com.alibaba.fastjson.JSON;
import com.giovanny.study.config.jwt.JwtConfig;
import com.giovanny.study.entity.po.SysUser;
import com.giovanny.study.security.entity.SelfUserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * @packageName: com.giovanny.study.utils
 * @className: JWTTokenUtil
 * @description: JWTTokenUtil
 * @author: YangJun
 * @date: 2020/5/12 16:25
 * @version: v1.0
 **/
@Slf4j
public class JwtTokenUtil {


    /**
     * 生成Token
     *
     * @param selfUserEntity selfUserEntity 用户安全实体
     * @return Token
     */
    public static String createAccessToken(SelfUserEntity selfUserEntity) {
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(selfUserEntity.getUserId() + "")
                // 主题
                .setSubject(selfUserEntity.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("giovanny")
                // 自定义属性 放入用户拥有权限
                .claim("authorities", JSON.toJSONString(selfUserEntity.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JwtConfig.secret)
                .compact();
        log.info("toke:[{}]", token);
        return token;
    }
}
