package com.example.demo.interceptor;

import com.example.demo.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Chu
 * @create 2022-03-13-19:11
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("token");
        String email = JwtUtils.parseEmail(jwt);
        if(email!=null){
            return true;
        }
        request.setAttribute("msg","请先登录");
        return false;
    }
}
