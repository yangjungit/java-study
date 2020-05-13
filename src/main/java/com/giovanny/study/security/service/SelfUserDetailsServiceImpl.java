package com.giovanny.study.security.service;

import com.giovanny.study.entity.po.SysUser;
import com.giovanny.study.security.entity.SelfUserEntity;
import com.giovanny.study.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SpringSecurity用户的业务实现
 *
 * @author YangJun
 */
@Component
public class SelfUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户信息
     *
     * @param username 用户名
     * @return UserDetails SpringSecurity用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public SelfUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser sysUserQuery = new SysUser();
        sysUserQuery.setUsername(username);
        List<SysUser> sysUserList = sysUserService.selectUserBy(sysUserQuery);
        if (sysUserList.size() > 0) {
            // 组装参数
            SelfUserEntity selfUserEntity = new SelfUserEntity();
            BeanUtils.copyProperties(sysUserList.get(0), selfUserEntity);
            return selfUserEntity;
        }
        return null;
    }
}