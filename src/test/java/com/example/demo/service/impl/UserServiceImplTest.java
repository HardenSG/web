package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Chu
 * @create 2022-03-18-13:21
 */
public class UserServiceImplTest {
    UserService userService = new UserServiceImpl();
    @Test
    void getUserByEmail(){
        System.out.println(userService.getUserByEmail("123@com"));
    }
}