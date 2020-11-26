package com.giovanny.study.service.impl;

import com.giovanny.study.entity.po.SysMenu;
import com.giovanny.study.mapper.SysMenuMapper;
import com.giovanny.study.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YangJun
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> list() {
        return sysMenuMapper.selectAll();
    }
}