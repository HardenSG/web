package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.service.*;
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
import java.util.List;
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
    @Autowired
    CommentsService commentsService;
    @Autowired
    TopicService topicService;



    /**
     * 添加动态
     * @param session 保存图片需要
     * @param request token->email
     * @param message 动态内容
     * @param picture 动态图片
     * @return
     */
    @GetMapping("/dynamic")
    public Map insertDynamic(HttpSession session,HttpServletRequest request,
                             @RequestParam(value = "message") String message,
                             @RequestParam(value = "picture",required = false) MultipartFile picture,
                             @RequestParam(value = "topic",required = false)String topic) {
        Map param = new HashMap();

        Integer dId = null;

        //email
        String token = request.getHeader("token");
        String email = JwtUtils.parseEmail(token);

        //content
        String content = message;

        //picture
        String photo = null;
        if(picture!=null) {

            try {
                photo = UploadUtils.upload(picture, session);
            } catch (IOException e) {
                param.put("code", "0");
                param.put("message", "文件上传失败");
            }
        }
        //date
        Date date = new Date(System.currentTimeMillis());
        //topic不为null，则搜索topic表中是否已存在该话题
        if(topic!=null){
            dId = topicService.searchTopicId(topic);
            if(dId==null){
                //did为空，话题表添加这条话题
                topicService.insertTopic(new Topic(topic, 0,photo));
                dId = topicService.searchTopicId(topic);
            }

        }
        //放进一个dynamic对象里
        Dynamic dynamic = new Dynamic(email,content,photo,date,dId,content);
        //进行添加
        //int i = dynamicService.insertDynamic(email, content, photo, date);
        int i = dynamicService.insertDynamic1(dynamic);
        if(i !=0) {
            User userByEmail = userService.getUserByEmail(email);
            param.put("topic",topic);
            param.put("dynamic",dynamic);
            param.put("user",userByEmail);
            param.put("status", "200");
            param.put("message","添加成功");
        }else {
            param.put("code", "0");
            param.put("message","添加失败");
        }
        return param;
    }

    /** 点赞
     * @param request token->email
     * @param like 点赞数
     * @param dId 动态id
     * @return
     */
    @PostMapping("/dynamic/like")
    public Map like(HttpServletRequest  request ,@RequestParam("likes") int like,@RequestParam("id") int dId){
        Map param = new HashMap();
        //获得email
        String email = JwtUtils.parseEmail(request.getHeader("token"));
        //得到动态
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

    /**
     * 转发
     * @param request
     * @param dId
     * @param content
     * @return
     */
    @GetMapping("/dynamic/forward")
    public Map forward(HttpServletRequest request , @RequestParam("dId") int dId , @RequestParam("forwardCount") int forwardCount , @RequestParam(required = false,value = "content") String content){
        Map map = new HashMap();
        //获得email
        String email = JwtUtils.parseEmail(request.getHeader("token"));
        //获得被转发的动态
        Dynamic dynamic = dynamicService.getDynamic(dId);
        //被转发的动态成为新的动态,并添加
        Dynamic newDynamic = dynamic;
        //两个属性表明此条动态为转发，原动态id为
        newDynamic.setOriginalId(dId);
        newDynamic.setContent(content);
        int i = dynamicService.insertDynamic(dynamic);
        if(i!=0){
            forwardCount = forwardCount+1;
            int count = dynamicService.updateByColumn("forward_count", forwardCount , dId);
            map.put("forwardCount",forwardCount);
            map.put("status","200");
            map.put("msg","转发成功");
        }else {
            map.put("status","0");
            map.put("msg","转发失败");
        }
        return map;
    }

    /**
     * 评论
     * @param comment 评论内容
     * @param dId 被评论的动态id
     * @param commentCount 被评论的评论数
     * @param request
     * @return
     */
    @PostMapping("/dynamic/comment")
    public Map comment(@RequestParam("comment") String comment , @RequestParam("dId") int dId , @RequestParam("commentCount") int commentCount, HttpServletRequest request){

        Map param = new HashMap();
        //获得email
        String email = JwtUtils.parseEmail(request.getHeader("token"));
        //添加信息到comments表
        Comments comments = new Comments(dId,email,comment);
        int i = commentsService.insertComment(comments);
        //获得点赞用户的信息
        User userByEmail = userService.getUserByEmail(email);
        if(i!=0){
            commentCount = commentCount+1;
            int count = dynamicService.updateByColumn("comment_count", commentCount , dId);
            param.put("user",userByEmail);
            param.put("dId",dId);
            param.put("commentCount",commentCount);
            param.put("status","200");
            param.put("msg","评论成功");
        }else {
            param.put("status","0");
            param.put("msg","评论失败");
        }
        return param;
    }

    /**
     * 无限滚动显示动态
     * @param pageNumber 页码
     * @param
     * @return
     */
    @PostMapping("/dynamic")
    public Map dynamicList(@RequestParam("page") int pageNumber ){
        int i = 0;
        Map map = new HashMap();
        //存放一条数据的map

        //得到五条dynamic记录
        List<Dynamic> list = dynamicService.pageList(pageNumber);
        for (Dynamic dynamic : list) {
            Map param = new HashMap();
            i++;
            //拿到动态的话题
            String topic = topicService.getTopic(dynamic.getTId());
            //拿到发动态的人的email
            String email = dynamic.getEmail();
            //拿到发此条动态的user
            User userByEmail = userService.getUserByEmail(email);
            //拿到至多5条评论
            List<Comments> comments = commentsService.getCommentsIncludeName(commentsService.selectCommentsByDidLimit(dynamic.getDId(),0,5));
            //原创
            if (dynamic.getOriginalId()==0){
                //type:0 说明是原创
                param.put("dynamicType","0");
            //删除
            }else if (dynamic.getOriginalId()==-2){
                param.put("dynamicType","-2");
            //转发
            }else {
                param.put("dynamicType","1");
                User originalUser = userService.getUserByEmail(dynamicService.getDynamic(dynamic.getOriginalId()).getEmail());
                param.put("originalUser",originalUser);
            }
            param.put("dynamic",dynamic);
            param.put("user",userByEmail);
            param.put("comments",comments);
            param.put("topic",topic);
            map.put("info"+i,param);
        }
        map.put("status",200);
        map.put("message","成功");
        return map;
    }
    @GetMapping ("/dynamic/comment")
    public Map getComments(@RequestParam("dId") int dId){
        Map param = new HashMap();
        List<Comments> comments = commentsService.selectCommentsByDid(dId);
        param.put("comments",comments);
        param.put("status",200);
        param.put("msg","评论显示成功");
        return param;
    }

    //删除动态
    @DeleteMapping("dynamic/delete")
    public Map deleteDynamic(HttpServletRequest request, Integer did, String email){
        return dynamicService.deleteDynamic(request,did,email);
    }
}

