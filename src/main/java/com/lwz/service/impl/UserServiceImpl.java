package com.lwz.service.impl;

import com.lwz.mapper.UserMapper;
import com.lwz.pojo.User;
import com.lwz.service.UserService;
import com.lwz.utils.Md5Util;
import com.lwz.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //根据查询用户名查询用户
    @Override
    public User findbyUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    //用户注册
    @Override
    public void register(String username, String password) {
        //MD5加密
        String md5String = Md5Util.getMD5String(password);
        //注册用户
        userMapper.add(username, md5String);
    }

    //修改用户信息
    @Override
    public void update(User user) {
        /*user.setUpdateTime(LocalDateTime.now());*/
        userMapper.update(user);
    }
    //更新头像
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);

    }
}
