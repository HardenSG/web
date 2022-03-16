package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private UserService userService;
    @Autowired
    JavaMailSenderImpl mailSender;
    String text = "";
    @PostMapping("regist")
    @ResponseBody
    public Map regist(@RequestParam("name") String name,
                      @RequestParam("password") String password,
                      @RequestParam("email")String email,
                      @RequestParam("code")String code )
    {
        HashMap param = new HashMap<>();
        if (!text.equals(code)) {
            param.put("status", 0);
            param.put("msg", "验证码不正确");
        } else if (text.equals(code)&&userService.userRepeat(email)==true){
            usermapper.savaUser(name, password, email);
            param.put("status", 200);
            param.put("msg","注册成功");
        }else{
            param.put("msg","注册失败" );
        }
        return param;
    }
    @PostMapping("/capture")
    public Map Captcha(String email){
        HashMap captcha = new HashMap<>();
        if(userService.userRepeat(email) == true){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件设置
        simpleMailMessage.setSubject("验证码");
        text=String.valueOf((int)((Math.random()*9+1)*1000));
        System.out.println(text);
        simpleMailMessage.setText("您好，验证码为："+text);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("rena2928992879@163.com");
        mailSender.send(simpleMailMessage);
        }else{
            captcha.put("msg","邮箱重复");
        }
        return captcha;
    }


}

