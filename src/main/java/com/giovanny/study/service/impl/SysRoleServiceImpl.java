package com.giovanny.study.service.impl;

import com.giovanny.study.entity.po.SysRole;
import com.giovanny.study.mapper.SysRoleMapper;
import com.giovanny.study.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YangJun
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> list() {
        return sysRoleMapper.selectAll();
    }
}