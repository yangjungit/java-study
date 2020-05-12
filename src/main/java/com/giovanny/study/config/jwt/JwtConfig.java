package com.giovanny.study.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 * @Author Sans
 * @CreateTime 2019/10/1 22:56
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * 密钥KEY
     */
    public static  String secret;
    /**
     * TokenKey
     */
    public static  String tokenHeader;
    /**
     * Token前缀字符
     */
    public static  String tokenPrefix;
    /**
     * 过期时间
     */
    public static  Integer expiration;
    /**
     * 不需要认证的接口
     */
    public  static  String antMatchers;


}