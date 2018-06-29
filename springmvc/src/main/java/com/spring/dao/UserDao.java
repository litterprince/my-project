package com.spring.dao;

import com.spring.po.UserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserDao {

    List<UserBean> findByParam(UserBean user);

    void batchInsertUsers(List<UserBean> users);

    void batchDeleteUsers(List<String> ids);

    int insert(UserBean userBean);
}
