package com.test.userinfo.thrift;

import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.Map;

public class UserInfoServiceImpl implements UserInfoService.Iface {
    private static Map<Integer, UserInfo> userMap = new HashMap<Integer, UserInfo>();

    static {
        userMap.put(1, new UserInfo(1,"mao","mao","男","2016"));
        userMap.put(2, new UserInfo(2,"ci","ci","女","07"));
        userMap.put(3, new UserInfo(3,"yuan","yuan","男","28"));
    }

    @Override
    public UserInfo lg_userinfo_getUserInfoById(int userid) throws TException {
        return userMap.get(userid);
    }

    @Override
    public String lg_userinfo_getUserNameById(int userid) throws TException {
        return userMap.get(userid).username;
    }

    @Override
    public int lg_userinfo_getUserCount() throws TException {
        return userMap.size();
    }

    @Override
    public boolean lg_userinfo_checkUserById(int userid) throws TException {
        return userMap.containsKey(userid);
    }
}
