package com.giovanny.study.utils;

import com.giovanny.study.security.entity.SelfUserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具类
 *
 * @author YangJun
 */
public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil() {
    }

    /**
     * 获取当前用户信息
     */
    public static SelfUserEntity getUserInfo() {
        return  (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取当前用户ID
     */
    public static Long getUserId() {
        return getUserInfo().getUserId();
    }

    /**
     * 获取当前用户账号
     */
    public static String getUserName() {
        return getUserInfo().getUsername();
    }
}