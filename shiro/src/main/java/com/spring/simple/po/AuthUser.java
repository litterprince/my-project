package com.spring.po;

import java.util.List;

public class AuthUser {
    private SysUserBean user;
    private List<SysRoleBean> roles;
    private List<SysPermissionBean> permissions;

    public SysUserBean getUser() {
        return user;
    }

    public void setUser(SysUserBean user) {
        this.user = user;
    }

    public List<SysRoleBean> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleBean> roles) {
        this.roles = roles;
    }

    public List<SysPermissionBean> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermissionBean> permissions) {
        this.permissions = permissions;
    }
}
