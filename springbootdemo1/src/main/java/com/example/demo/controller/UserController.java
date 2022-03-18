package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService = new UserServiceImpl();
    //测试2
//    @RequestMapping("/user/{email}/{password}")
//    public User test2(@PathVariable("email") String email , @PathVariable("password") String password){
//        User userByEmail = this.userService.getUserByEmail(email);
//        if(userByEmail.getEmail().equals(email)&&userByEmail.getPassword().equals(password)){
//            userByEmail.setToken(JwtUtils.jwt());
//            return userByEmail;
//        }
//        return null;
//    }
    @PostMapping ("/user")
    public Map login(User user, HttpSession session, Model model){
        Map param = new HashMap<>();
        User userByEmail = this.userService.getUserByEmailAndPassword(user.getEmail(),user.getPassword());
        if(userByEmail==null){
            param.put("status","0");
            param.put("msg","账号密码错误");
        }else if(userByEmail.getEmail().equals(user.getEmail())&&userByEmail.getPassword().equals(user.getPassword())){
            userByEmail.setToken(JwtUtils.jwt(userByEmail.getEmail(),userByEmail.getPassword()));
            param.put("status","200");
            param.put("msg","登陆成功");
            param.put("user",userByEmail);
            return param;
        }else {
            param.put("status","0");
            param.put("msg","账号密码错误");
        }
        return param;
    }




}

