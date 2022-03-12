package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@RestController
@RequestMapping("//user")
public class UserController {
    @Autowired
    private UserService userService = new UserServiceImpl();
    @RequestMapping("/guess")
    public List<User> guess(){
        return this.userService.list();
    }

}

