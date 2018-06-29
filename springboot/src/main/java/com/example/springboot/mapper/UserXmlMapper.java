package com.example.springboot.mapper;


import com.example.springboot.po.UserBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("userXmlMapper")
public interface UserXmlMapper {

    List<UserBean> getUsersByParam(UserBean user);

    void batchInsertUsers(List<UserBean> users);

    void batchDeleteUsers(List<String> ids);
}
