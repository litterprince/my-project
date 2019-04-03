package com.spring.service;

import com.spring.dao.UserDao;
import com.spring.po.SysUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    @Autowired
    UserDao userDao;

    public int insertOne(SysUserBean sysUserBean){
        return userDao.insertOne(sysUserBean);
    }

    public List<SysUserBean> findByParam(SysUserBean user){
        return userDao.findByParam(user);
    }
}
