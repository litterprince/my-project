package com.example.springboot.mapper;


import com.example.springboot.po.UserBean;

import java.util.List;

public interface UserXmlMapper {

    List<UserBean> getUsersByParam(UserBean user);

    void batchInsertUsers(List<UserBean> users);

    void batchDeleteUsers(List<String> ids);
}
