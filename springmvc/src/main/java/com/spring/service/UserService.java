package com.spring.service;

import com.spring.dao.UserDao;
import com.spring.po.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserService {
    @Autowired
    UserDao userDao;

    public int insertOne(UserBean userBean){
        return userDao.insert(userBean);
    }

    public List<UserBean> findByParam(UserBean user){
        return userDao.findByParam(user);
    }
}
