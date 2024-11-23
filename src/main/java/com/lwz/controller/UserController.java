package com.lwz.controller;

import com.auth0.jwt.JWT;
import com.lwz.pojo.Result;
import com.lwz.pojo.User;
import com.lwz.service.UserService;
import com.lwz.utils.JwtUtil;
import com.lwz.utils.Md5Util;
import com.lwz.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //注册接口
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$",message = "用户名要求5-16位数") String username,
                           @Pattern(regexp = "^\\S{5,16}$",message = "密码要求5-16位数") String password){
        //查询用户
        User user = userService.findbyUserName(username);
        if (user == null){
            //用户名为空，注册用户.
            userService.register(username, password);
            return Result.success();
        }else {
            //用户名不为空,返回提示信息.
            return Result.error("用户名已存在");
        }
    }

    //登录接口
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                                @Pattern(regexp = "^\\S{5,16}$") String password){
        User loginuser = userService.findbyUserName(username);
        if (loginuser == null){
            //用户为空，返回提示信息.
            return Result.error("用户不存在");
        }else {
            //用户不为空，判断密码是否正确.
            if (Md5Util.getMD5String(password).equals(loginuser.getPassword())){
                //密码正确，生成token令牌.
                Map<String, Object> claims = new HashMap<>();// 创建一个Map对象存储JWT的负载信息
                claims.put("id",loginuser.getId()); // 将用户的唯一标识id存入负载中，以便于后续验证用户身份
                claims.put("username",loginuser.getUsername());// 将用户名存入负载中，用于在令牌中标识用户
                String token = JwtUtil.genToken(claims);// 调用JwtUtil的genToken方法生成JWT令牌
                //把token存储到Redis中
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                //设置token过期时间为1小时
                operations.set(token,token,1, TimeUnit.HOURS);
                return Result.success(token);// 返回生成的令牌，此处表示操作成功
            }
            return Result.error("密码错误");
        }
    }

    //用户信息
    @GetMapping("userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        //根据用户名查询用户信息
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findbyUserName(username);
        return Result.success(user);
    }

    //修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody @Validated Map<String, String> params,@RequestHeader("Authorization") String token) {
        // 1. 参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少参数错误");
        }

        // 两次密码是否一致
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码不一致");
        }

        // 原始密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findbyUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原始密码不正确");
        }

        // 2. 调用Service完成密码更新
        userService.updatePwd(newPwd);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

}
