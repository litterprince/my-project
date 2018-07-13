package com.spring.dao;

import com.spring.base.BaseDao;
import com.spring.po.SysUserBean;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDao extends BaseDao<SysUserBean> {

}
