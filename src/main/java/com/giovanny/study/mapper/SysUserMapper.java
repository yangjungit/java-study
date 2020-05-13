package com.giovanny.study.mapper;

import com.giovanny.study.config.mybatis.MyMapper;
import com.giovanny.study.entity.po.SysMenu;
import com.giovanny.study.entity.po.SysRole;
import com.giovanny.study.entity.po.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author YangJun
 */
public interface SysUserMapper extends MyMapper<SysUser> {
    /**
     * 查询用户的所有权限
     *
     * @param userId userId
     * @return List<SysMenu>
     */
    List<SysMenu> selectSysMenuByUserId(@Param("userId") Long userId);

    List<SysRole> selectSysRoleByUserId(@Param("userId") Long userId);
}