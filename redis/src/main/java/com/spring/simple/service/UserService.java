package com.spring.service;

import com.spring.dao.UserDao;
import com.spring.po.SysUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    @Autowired
    UserDao userDao;

    public int insertOne(SysUserBean sysUserBean){
        return userDao.insertOne(sysUserBean);
    }

    @Cacheable("findByParam") //标注该方法查询的结果进入缓存，再次访问时直接读取缓存中的数据
    public List<SysUserBean> findByParam(SysUserBean user){
        return userDao.findByParam(user);
    }
}
