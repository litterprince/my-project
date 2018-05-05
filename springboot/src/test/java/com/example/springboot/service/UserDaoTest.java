package com.example.springboot.service;

import com.example.springboot.mapper.UserMapper;
import com.example.springboot.po.UserBean;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void getAll() throws Exception {
        List<UserBean> users =  userMapper.getAll();
        assertEquals(6, users.size());
    }

    @Test
    @Ignore
    public void getCount() throws Exception {
    }

    @Test
    @Ignore
    public void getOne() throws Exception {
    }

    @Test
    @Ignore
    public void insert() throws Exception {
    }

    @Test
    @Ignore
    public void update() throws Exception {
    }

    @Test
    @Ignore
    public void delete() throws Exception {
    }

    @Test
    @Ignore
    public void batchDeleteUsers() throws Exception {
    }

}