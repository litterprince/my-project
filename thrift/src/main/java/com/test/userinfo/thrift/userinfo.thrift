namespace java com.test.userinfo

/*结构体类型：*/
struct UserInfo{
    1:i32 userid,
    2:string username,
    3:string userpwd,
    4:string sex,
    5:string age,
}

/*服务类型：*/
service UserInfoService{
    UserInfo lg_userinfo_getUserInfoById(1:i32 userid),
    string   lg_userinfo_getUserNameById(1:i32 userid),
    i32      lg_userinfo_getUserCount(),
    bool     lg_userinfo_checkUserById(1:i32 userid),
 }