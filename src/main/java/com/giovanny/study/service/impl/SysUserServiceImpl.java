package com.giovanny.study.service.impl;


import com.giovanny.study.entity.po.SysMenu;
import com.giovanny.study.entity.po.SysRole;
import com.giovanny.study.entity.po.SysUser;
import com.giovanny.study.mapper.SysUserMapper;
import com.giovanny.study.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<SysUser> list() {
        return sysUserMapper.selectAll();
    }


    /**
     *
     * @param sysUserQuery SysUser
     * @return SysUser
     */
    @Override
    public List<SysUser> selectUserBy(SysUser sysUserQuery) {
        return sysUserMapper.select(sysUserQuery);

    }

}