package com.spring.service;

import com.spring.dao.UserMongoDao;
import com.spring.po.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    @Autowired
    UserMongoDao userDao;

    public List<UserBean> findList(int skip, int limit){
        return userDao.findList(skip, limit);
    }
}
