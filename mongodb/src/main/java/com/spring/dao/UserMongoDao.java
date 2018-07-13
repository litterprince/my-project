package com.spring.dao;

import com.spring.base.BaseMongoDaoImpl;
import com.spring.po.UserBean;
import org.springframework.stereotype.Repository;

@Repository
public class UserMongoDao extends BaseMongoDaoImpl<UserBean> {

}
