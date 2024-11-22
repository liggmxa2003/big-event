package com.lwz.interceptors;

import com.lwz.utils.JwtUtil;
import com.lwz.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存入ThreadLocal
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //响应状态码
            response.setStatus(401);
            //拦截
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求结束，移除线程变量中的数据，避免内存泄漏
        ThreadLocalUtil.remove();
    }
}
