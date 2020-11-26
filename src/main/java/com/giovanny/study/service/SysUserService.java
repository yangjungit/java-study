package com.giovanny.study.service;


import com.giovanny.study.entity.po.SysMenu;
import com.giovanny.study.entity.po.SysRole;
import com.giovanny.study.entity.po.SysUser;

import java.util.List;

/**
 * @author YangJun
 */
public interface SysUserService {

    List<SysUser> list();

    List<SysUser> selectUserBy(SysUser sysUserQuery);

    List<SysMenu> selectSysMenuByUserId(Long userId);

    List<SysRole> selectSysRoleByUserId(Long userId);

    int insert(SysUser user);
}