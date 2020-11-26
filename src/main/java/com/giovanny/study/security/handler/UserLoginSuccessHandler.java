package com.giovanny.study.security.handler;

import com.giovanny.study.config.jwt.JwtConfig;
import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.security.entity.SelfUserEntity;
import com.giovanny.study.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录成功处理类
 * @author: YangJun
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 组装JWT
        SelfUserEntity selfUserEntity = (SelfUserEntity) authentication.getPrincipal();
        String token = JwtTokenUtil.createAccessToken(selfUserEntity);
        token = JwtConfig.tokenPrefix + token;
        // 封装返回参数
        MyResponse.responseJson(response, MyResponse.success("登录成功", token));
    }
}