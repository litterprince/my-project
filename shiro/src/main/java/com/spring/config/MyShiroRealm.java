package com.spring.config;

import com.spring.po.AuthUser;
import com.spring.po.SysPermissionBean;
import com.spring.po.SysRoleBean;
import com.spring.po.SysUserBean;
import com.spring.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        List<String> roleList = new ArrayList<String>();
        List<String> permissionList = new ArrayList<String>();
        AuthUser authUser = (AuthUser) super.getAvailablePrincipal(principalCollection);
        List<SysRoleBean> roles = authUser.getRoles();
        List<SysPermissionBean> permissions = authUser.getPermissions();
        for(SysRoleBean role : roles){
            roleList.add(role.getName());//admin
        }
        for(SysPermissionBean per : permissions){
            permissionList.add(per.getPercode());//user:create
        }
        // 为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRoles(roleList);
        simpleAuthorInfo.addStringPermissions(permissionList);
        return simpleAuthorInfo;
    }

    /**
     * 登陆验证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取凭证账号
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        SysUserBean param = new SysUserBean();
        param.setUserName(userName);
        List<SysUserBean> userList = userService.findByParam(param);
        if(userList!=null && userList.size()>0){
            SysUserBean user = userList.get(0);
            List<SysRoleBean> roles = new ArrayList<>();
            List<SysPermissionBean> permissions = new ArrayList<>();
            AuthUser authUser = new AuthUser();
            authUser.setUser(user);
            authUser.setRoles(roles);
            authUser.setPermissions(permissions);
            // 组装的info
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    authUser, user.getPassword(), ByteSource.Util.bytes(user.getSalt()),  this.getClass().getName());
            return simpleAuthenticationInfo;
        }else{
            throw new AuthenticationException();
        }
    }
}
