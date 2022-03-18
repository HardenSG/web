package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.example.demo.entity.Dynamic;
import com.example.demo.entity.Like;
import com.example.demo.entity.User;
import com.example.demo.service.DynamicService;
import com.example.demo.service.LikeService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Chu
 * @since 2022-03-15
 */
@RestController

public class DynamicController {
    @Autowired
    private UserService userService;
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private LikeService likeService;


    //添加动态
    @GetMapping("/dynamic")
    public Map insertDynamic(HttpSession session,HttpServletRequest  request, @RequestParam(value = "message") String message, @RequestParam(value = "picture") MultipartFile picture)
    {
        Map param = new HashMap();
        //email
        String token = request.getHeader("token");
        String email = JwtUtils.parseEmail(token);

        //content
        String content = message;
        //picture
        String photo = null;
        try {
            photo = UploadUtils.upload(picture,session);
        } catch (IOException e) {
            param.put("code", "0");
            param.put("message","文件上传失败");
        }
        //date
        Date date = new Date(System.currentTimeMillis());
        //放进一个dynamic对象里
        Dynamic dynamic = new Dynamic(email,content,photo,date);
        //进行添加
        //int i = dynamicService.insertDynamic(email, content, photo, date);
        int i = dynamicService.insertDynamic1(dynamic);
        if(i !=0) {
            User userByEmail = userService.getUserByEmail(email);
            param.put("dynamic",dynamic);
            param.put("user",userByEmail);
            param.put("code", "200");
            param.put("message","添加成功");
        }else {
            param.put("code", "0");
            param.put("message","添加失败");
        }
        return param;
    }
    @PostMapping("/dynamic/like")
    //点赞
    public Map like(HttpServletRequest  request ,@RequestParam("like") int like,@RequestParam("id") int dId){
        Map param = new HashMap();
        //获得email
        String email = JwtUtils.parseEmail(request.getHeader("token"));
        //得到动态id
        Dynamic byId = dynamicService.getById(dId);
        if(likeService.queryLike(email,dId)) {
            //更新点赞数
            like = like + 1;
            byId.setLikes(like);
            Like like1 = new Like(dId,email);
            //数据库更新
            int count = dynamicService.updateByColumn("likes", like, dId);
            int i = likeService.insertLike(like1);
            if(count==0||i==0){
                param.put("status", "0");
                param.put("message", "点赞失败");
            }

            param.put("like", like);
            param.put("status", "200");
            param.put("message", "点赞成功-w-");

        }else {
            param.put("status", "1");
            param.put("message", "您已经点过赞了");
        }
        return param;
    }

}

