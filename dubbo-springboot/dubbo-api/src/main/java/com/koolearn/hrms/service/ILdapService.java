package com.koolearn.hrms.service;

import com.xiaoze.api.model.LdapUser;

import java.util.List;

/**
 * Created by lixinjun on 2018/4/8.
 */
public interface ILdapService {

    /**
     * 校验邮箱，密码
     * @param email 邮箱，可以不带@koolearn.com 也可以带
     * @param password 密码原文
     * @return true:校验通过 false:校验失败
     */
    boolean verifyLdapUser(String email, String password);

    /**
     * 查询用户列表，可以指定过滤条件
     * @param filter 过滤条件（需要满足ldap过滤条件语法）
     * @return 满足条件的用户
     */
    List<LdapUser> queryUser(String filter);

    /**
     * 查询所有用户列表
     * @return 用户列表，包含两个字段：邮箱名(不包含@koolearn.com) 、中文姓名
     */
    List<LdapUser> queryUserAll();

    /**
     *
     * @param filter
     * @return
     */
    List<LdapUser> queryUserWithJZ(String filter);

    /**
     *
     * @return
     */
    List<LdapUser> queryUserAllWithJZ();

    /**
     * 获取有效域用户信息
     * @param userName 用户名
     * @return
     */
    LdapUser getUserByName(String userName);

    /**
     * 获取可用的域帐号
     * @param domainAccount
     * @param i
     * @return
     */
    String getValidDomainAccount(String domainAccount, int i);

    /**
     * 打印属性
     * @param realName
     * @param department
     */
    void printAttributes(String realName, String department);

    /**
     * 获取所有域用户
     * @return
     */
    List<LdapUser> getAll();

    /**
     * 获取所有group
     * @return
     */
    List<String> getAllGroup(String filter);

}
