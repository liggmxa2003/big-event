package com.lwz.service;

import com.lwz.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {

    //根据用户名查询用户
    User findbyUserName(String username);
    //注册用户
    void register(String username, String password);
    //修改用户信息
    void update(User user);
    //更新用户头像
    void updateAvatar(String avatarUrl);
    //修改密码
    void updatePwd(String newPwd);
}
