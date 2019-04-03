package com.spring.dao;

import com.spring.base.BaseDao;
import com.spring.po.SysUserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserDao extends BaseDao<SysUserBean> {

}
