package com.giovanny.study.controller;

import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.entity.po.SysMenu;
import com.giovanny.study.entity.po.SysRole;
import com.giovanny.study.entity.po.SysUser;
import com.giovanny.study.security.entity.SelfUserEntity;
import com.giovanny.study.service.SysMenuService;
import com.giovanny.study.service.SysRoleService;
import com.giovanny.study.service.SysUserService;
import com.giovanny.study.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @packageName: com.giovanny.study.controller
 * @className: SecurityController
 * @description: SecurityController
 * @author: YangJun
 * @date: 2020/5/12 18:04
 * @version: v1.0
 **/
@RestController
@Slf4j
public class SecurityController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;


    @PostMapping(path = "/author/register")
    public MyResponse register(SysUser user) {
        user.setSalt(user.getPassword());
        user.setStatus("NORMAL");
        int insert = sysUserService.insert(user);
        if (insert > 0) {
            return MyResponse.success("注册成功");
        } else if (insert == -1) {
            return MyResponse.failed(1001, "用户名重复", user);
        } else {
            return MyResponse.failed(403, "注册失败", user);

        }
    }


    /**
     * 管理端信息
     *
     * @return MyResponse
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/info", method = RequestMethod.GET)
    public MyResponse userLogin() {
        SelfUserEntity userDetails = SecurityUtil.getUserInfo();
        return MyResponse.success("管理端信息", userDetails);
    }


    /**
     * 拥有ADMIN或者USER角色可以访问
     *
     * @return MyResponse
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = "/admin/list", method = RequestMethod.GET)
    public MyResponse list() {
        List<SysUser> sysUserEntityList = sysUserService.list();
        return MyResponse.success("拥有用户或者管理员角色都可以查看", sysUserEntityList);
    }

    /**
     * 拥有ADMIN和USER角色可以访问
     *
     * @return MyResponse
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @RequestMapping(value = "/admin/menuList", method = RequestMethod.GET)
    public MyResponse menuList() {
        List<SysMenu> sysMenuEntityList = sysMenuService.list();
        return MyResponse.success("拥有用户和管理员角色都可以查看", sysMenuEntityList);
    }


    /**
     * 拥有sys:user:info权限可以访问
     * hasPermission 第一个参数是请求路径 第二个参数是权限表达式
     *
     * @return MyResponse
     */
    @PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
    @RequestMapping(value = "/admin/userList", method = RequestMethod.GET)
    public MyResponse userList() {
        List<SysUser> sysUserEntityList = sysUserService.list();
        return MyResponse.success("拥有sys:user:info权限都可以查看", sysUserEntityList);
    }


    /**
     * 拥有ADMIN角色和sys:role:info权限可以访问
     *
     * @return MyResponse
     */
    @PreAuthorize("hasRole('ADMIN') and hasPermission('/admin/adminRoleList','sys:role:info')")
    @RequestMapping(value = "/admin/adminRoleList", method = RequestMethod.GET)
    public MyResponse adminRoleList() {
        List<SysRole> sysRoleEntityList = sysRoleService.list();
        return MyResponse.success("拥有ADMIN角色和sys:role:info权限可以访问", sysRoleEntityList);
    }


    @RequestMapping(value = "/index/info", method = RequestMethod.GET)
    public MyResponse index() {
        // 组装参数
        return MyResponse.success("这里是首页不需要权限和登录拦截", null);
    }


    /**
     * 用户端信息
     *
     * @return MyResponse
     */
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public MyResponse userInfo() {
        SelfUserEntity userDetails = (SelfUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return MyResponse.success("用户端信息", userDetails);
    }

    /**
     * 拥有USER角色和sys:user:info权限可以访问
     */
    @PreAuthorize("hasRole('USER') and hasPermission('/user/menuList','sys:user:info')")
    @RequestMapping(value = "/user/menuList", method = RequestMethod.GET)
    public MyResponse sysMenuEntity() {
        List<SysMenu> sysMenuEntityList = sysMenuService.list();
        return MyResponse.success("拥有USER角色和sys:user:info权限可以访问", sysMenuEntityList);
    }

}
