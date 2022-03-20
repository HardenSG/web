package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.UploadUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
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
@ResponseBody
public class UserController {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private UserService userService;

    @Autowired
    JavaMailSenderImpl mailSender;
    String text = "";
    @PostMapping("regist")

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
    @PostMapping("capture")
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
   //返回加工过的url
    @PostMapping("url")
    public String url(MultipartFile photo, HttpSession session) throws IOException {
       return UploadUtils.upload(photo,session);
    }
    //个人资料的修改
    @PutMapping("updateData")
    public Map updateData(User user, HttpServletRequest request,@RequestParam("email") String email) {
       return userService.changeBy(user,request);
    }
    //个人资料的展示
    @GetMapping("showData")
    public  Map showData(HttpServletRequest request){
        return userService.selectBy(request);

    }






}

