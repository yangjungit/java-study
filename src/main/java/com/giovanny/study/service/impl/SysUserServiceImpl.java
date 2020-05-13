package com.giovanny.study.service.impl;


import com.giovanny.study.entity.po.SysMenu;
import com.giovanny.study.entity.po.SysRole;
import com.giovanny.study.entity.po.SysUser;
import com.giovanny.study.mapper.SysUserMapper;
import com.giovanny.study.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 系统用户业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<SysUser> list() {
        return sysUserMapper.selectAll();
    }


    /**
     * @param sysUserQuery SysUser
     * @return SysUser
     */
    @Override
    public List<SysUser> selectUserBy(SysUser sysUserQuery) {
        return sysUserMapper.select(sysUserQuery);

    }

    /**
     * 根据用户ID查询权限集合
     *
     * @param userId userId
     * @return List<SysMenu>
     */
    @Override
    public List<SysMenu> selectSysMenuByUserId(Long userId) {
        return sysUserMapper.selectSysMenuByUserId(userId);
    }

    /**
     * 根据用户ID查询角色集合
     *
     * @param userId userId
     * @return List<SysRole>
     */
    @Override
    public List<SysRole> selectSysRoleByUserId(Long userId) {
        return sysUserMapper.selectSysRoleByUserId(userId);
    }

    @Override
    public int insert(SysUser user) {
        SysUser sysUserQuery = new SysUser();
        sysUserQuery.setUsername(user.getUsername());
        List<SysUser> userList = sysUserMapper.select(sysUserQuery);
        if (userList.size() == 0) {
            String encodePwd = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodePwd);
            return sysUserMapper.insert(user);
        } else {
            return -1;
        }
    }
}