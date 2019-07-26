package com.spring.simple.dao;

import com.spring.base.BaseDao;
import com.spring.simple.po.SysUserBean;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDao extends BaseDao<SysUserBean> {

}
