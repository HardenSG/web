package com.example.demo.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Chu
 * @since 2022-03-15
 */
@RestController
@ResponseBody
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
    @Autowired
    FollowService followService;
    @Autowired
    BlackListService blackListService;
    @Autowired
    DynamicPictureService dynamicPictureService;
    @Autowired
    ForwardService forwardService;




    /**
     * 添加动态
     * @param request token->email
     * @param message 动态内容
     * @return
     */
    @PostMapping("/dynamic")
    public Map insertDynamic(HttpServletRequest request,
                             @RequestParam(value = "message") String message,
                             @RequestParam(value = "picture",required = false) String pictures,
                             @RequestParam(value = "topic",required = false)String topic) {
        Map param = new HashMap();
        //处理picture
        String[] picture = new String[0];
        try {
            picture = pictures.split(",");
        } catch (Exception e) {

        }

        Integer tId = null;

        //email
        String token = request.getHeader("token");
        String email = JwtUtils.parseEmail(token);

        //content
        String content = message;

        //date
        Date date = new Date(System.currentTimeMillis());
        //topic不为null，则搜索topic表中是否已存在该话题
        if(topic!=null){
             tId = topicService.searchTopicIdNoHot(topic);
            if(tId==null){
                //did为空，话题表添加这条话题
                try {
                    topicService.insertTopic(new Topic(topic, 0,picture[0],date));
                }catch (Exception e){
                    topicService.insertTopic(new Topic(topic, 0,"82.157.48.184:8080/pictures/e9c2d6ab-6816-40b5-8954-06dcb0161877.jpg",date));
                }
                tId = topicService.searchTopicIdNoHot(topic);
            }
        }

        //放进一个dynamic对象里
        Dynamic dynamic = new Dynamic(email,content,date,tId,content);
        //进行添加
        int i = dynamicService.insertDynamic(dynamic);
        //图片添加到动态图片表里
        List picturess = new LinkedList();
        if(picture.length > 0){
            for (String photo : picture) {
                dynamicPictureService.insertDynamicPicture(new DynamicPicture(null,dynamic.getDId(),photo));
                picturess.add(photo);
            }
        }
        if(i !=0) {
            User userByEmail = userService.getUserByEmail(email);
            param.put("topic",topic);
            param.put("dynamic",dynamic);
            param.put("pictures",picturess );
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
            Like like1 = new Like(dId,email,new Date(System.currentTimeMillis()));
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
        Dynamic newDynamic = dynamicService.getDynamic(dId);
        List<String> list = dynamicPictureService.queryPicure(dId);
        //被转发的动态成为新的动态,并添加
        newDynamic.setLikes(0);
        newDynamic.setCommentCount(0);
        newDynamic.setForwardCount(0);
        //两个属性表明此条动态为转发，原动态id为
        newDynamic.setOriginalId(dId);
        newDynamic.setForwardComment(content);
        newDynamic.setEmail(email);
        newDynamic.setDate(new Date(System.currentTimeMillis()));
        int i = dynamicService.insertDynamic(newDynamic);
        //转发表
        forwardService.insert(new Forward(null,Integer.toString(dId),email,new Date(System.currentTimeMillis()),0));
        for (String url:list) {
            DynamicPicture dynamicPicture = new DynamicPicture(null,newDynamic.getDId(),url);
            dynamicPictureService.insertDynamicPicture(dynamicPicture);
        }

        if(i!=0){
            int newforwardCount = forwardCount+1;
            int count = dynamicService.updateByColumn("forward_count", newforwardCount , dId);
            map.put("forwardCount",newforwardCount);
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
    public Map comment(@RequestParam("comment") String comment , @RequestParam("dId") int dId ,
                       @RequestParam("commentCount") int commentCount,
                       @RequestParam("dynamicUserEmail") String dynamicUserEmail,
                       HttpServletRequest request,
                       @RequestParam("commentIdP")int commentIdP,
                       @RequestParam("commentIdR") int commentIdR
    ){

        Map param = new HashMap();
        //获得用户email
        String email = JwtUtils.parseEmail(request.getHeader("token"));
        //检查黑名单
        BlackList blackList = new BlackList(dynamicUserEmail, email);
        if(blackListService.isExist(blackList)){
            param.put("status","200");
            param.put("msg","您暂时无法评论");
            return param;
        }
        //获得现在时间
        Date date = new Date(System.currentTimeMillis());
        //添加信息到comments表
        Comments comments = new Comments(dId,email,comment,date,commentIdR,commentIdP);
        int i = commentsService.insertComment(comments);


        //获得当前用户的信息
        User userByEmail = userService.getUserByEmail(email);
        if(i!=0){
            commentCount = commentCount+1;
            int count = dynamicService.updateByColumn("comment_count", commentCount , dId);
            param.put("email",email);
            param.put("date",date);
            param.put("user",userByEmail);
            param.put("dId",dId);
            param.put("commentCount",commentCount);
            param.put("status","200");
            param.put("msg","评论成功");

        }else{
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
    @GetMapping("/dynamic")
    public Map dynamicList(@RequestParam("page") int pageNumber ){

        Map map = new HashMap();
        //得到五条dynamic记录
        List<Dynamic> list = dynamicService.pageList(pageNumber);
        showDynamic(list,map);
        return map;
    }
    public void showDynamic(List<Dynamic>list ,Map map){
        int i = 0;
        if(list.size()<5){
            map.put("message","已经到底啦！");
        }else {
            map.put("message","成功");
        }
        for (Dynamic dynamic : list) {
            Map param = new HashMap();
            i++;
            //拿到动态的话题
            String topic = topicService.getTopic(dynamic.getTId());
            //拿到发动态的人的email
            String email = dynamic.getEmail();
            //拿到发此条动态的user
            User userByEmail = userService.getUserByEmail(email);
            //拿到动态图片
            List pictures = dynamicPictureService.queryPicure(dynamic.getDId());
            param.put("pictures",pictures);
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
                List originalPicture = dynamicPictureService.queryPicure(dynamic.getOriginalId());
                param.put("pictures",originalPicture);
                param.put("forwardComment",dynamic.getForwardComment());
            }
            param.put("dynamic",dynamic);
            param.put("user",userByEmail);
            param.put("topic",topic);
            map.put("info"+i,param);
        }
        map.put("status",200);
    }

//    /**
//     * 所有评论
//     * @param dId
//     * @return
//     */
//    @GetMapping ("/dynamic/comment")
//    public Map getComments(@RequestParam("dId") int dId){
//        Map param = new HashMap();
//        //所有评论
//        List<Comments>comments=commentsService.selectCommentsByDid(dId);
//        //一级评论
//        List<Comments> commentOne = commentsService.getCommentOne(dId);
//        int i=0;
//        HashMap<Object, Object> commentParent = new HashMap<>();
//        //遍历一级评论，comment1一条一级评论
//        for(Comments comment1:commentOne){
//            int j=0;
//            i++;
//            commentParent.put("一级评论"+i,comment1);
//            //遍历全部评论，comment指不定那条评论
//            HashMap<Object, Object> commentTwo = new HashMap<>();
//            for (Comments comment2:comments){
//                int t=0;
//                j++;
//                //指不定那条评论的pid是一级评论的commentID且条件是pid=rid，说明是二级评论不是三级，此if拿到了二级评论
//                if(comment2.getReplyId()==comment2.getParentId()&&comment2.getParentId()==comment1.getCommentId()){
//                    commentTwo.put("二级评论"+j,comment2);
//                    //如果二级评论的commentId是三级评论的rid,一级评论的cId是三级评论的pid
//                    HashMap<Object, Object> commentThree = new HashMap<>();
//                    for(Comments comment3:comments){
//                        if(comment2.getCommentId()==comment3.getReplyId()&&comment1.getCommentId()==comment3.getParentId()){
//                            commentThree.put("三级评论"+t,comment3);
//                        }
//                    }
//                    commentTwo.put("三级评论",commentThree);
//                }
//        }
//            commentParent.put("二级评论",commentTwo);
//        }
//        param.put("一级评论",commentParent);
//        param.put("status",200);
//        param.put("msg","评论显示成功");
//        return param;
//    }


    //删除动态
    @DeleteMapping("dynamic/delete")
    public Map deleteDynamic(HttpServletRequest request, Integer did, String email){
        return dynamicService.deleteDynamic(request,did,email);
    }


    //评论通知
    @GetMapping("/dynamic/commentNotice")
    public Map commentNotice(HttpServletRequest request){
        return dynamicService.commentNotice(request);
    }

    //点赞通知
    @GetMapping("/dynamic/likeNotice")
    public Map likeNotice(HttpServletRequest request) {
        return dynamicService.likeNotice(request);
    }

    //转发通知
    @GetMapping("/dynamic/forwardNotice")
    public Map forwardNotice(HttpServletRequest request) {
        return dynamicService.forwardNotice(request);
    }
    /**
     *关注人的动态
     * @param request
     * @return
     */
    @GetMapping("/dynamic/follow")
    public Map followDynamic(HttpServletRequest request,@RequestParam("pageNumber")int pageNumber) {
        Map map = new HashMap();
        //得到用户email
        String userEmail = JwtUtils.parseEmail(request.getHeader("token"));
        //得到关注表对象
        List<Follow> follows = followService.getFollowByUserEmail(userEmail);
        if(follows.size()!=0) {
            //查找动态
            List<Dynamic> list = dynamicService.getDynamicByFollow(follows, pageNumber);
            showDynamic(list, map);
        }else {
            map.put("status",200);
            map.put("message","您还没有关注任何人哦:(");
        }
        return map;
    }

    /**
     * 获得全部未读消息数
     * @return
     */
    @GetMapping("/notices")
    public int returnNotice(HttpServletRequest request){
        //得到用户email
        String userEmail = JwtUtils.parseEmail(request.getHeader("token"));
        return dynamicService.noticeCount(userEmail);
    }


    @GetMapping
    public Map getDynamicOne(){
        Map map = new HashMap();
        return map;
    }

    /**
     * 所有评论
     * @param dId
     * @return
     */
    @GetMapping ("/dynamic/comment")
    public Map getComments(@RequestParam("dId") int dId) {
        Map mapp = new HashMap();
        int i =0;
        List<Comments> commentOnes = commentsService.getCommentOne(dId);
        for(Comments commentone:commentOnes){
            i++;
            List<Comments> commentTwos = commentsService.getCommentTwo(dId, commentone.getCommentId());
            Map map1 = new HashMap();
            List comments1 = new LinkedList();
            for (Comments commenttwo : commentTwos) {

                List<Comments> commentThree = commentsService.getCommentThree(dId, commentone.getCommentId(), commenttwo.getCommentId());
                Map map2 = new HashMap();
                map2.put("comments",commentThree) ;
                map2.put("content",commenttwo);
                comments1.add(map2);
            }
            map1.put("comments",comments1);
            map1.put("content",commentone);
            mapp.put("info"+i,map1);
        }
        return mapp;
    }
    @GetMapping("dynamic/details")
    public  Map dynamciDetails(int dId) {

        Dynamic dynamic = dynamicService.getDynamic(dId);
        Map param = new HashMap();
        //拿到动态的话题
        String topic = topicService.getTopic(dynamic.getTId());
        //拿到发动态的人的email
        String email = dynamic.getEmail();
        //拿到发此条动态的user
        User userByEmail = userService.getUserByEmail(email);
        //拿到动态图片
        List pictures = dynamicPictureService.queryPicure(dynamic.getDId());
        //原创
        if (dynamic.getOriginalId() == 0) {
            //type:0 说明是原创
            param.put("dynamicType", "0");
            //删除
        } else if (dynamic.getOriginalId() == -2) {
            param.put("dynamicType", "-2");
            //转发
        } else {
            param.put("dynamicType", "1");
            User originalUser = userService.getUserByEmail(dynamicService.getDynamic(dynamic.getOriginalId()).getEmail());
            param.put("originalUser", originalUser);
            pictures = dynamicPictureService.queryPicure(dynamic.getOriginalId());
        }
        param.put("dynamic", dynamic);
        param.put("pictures", pictures);
        param.put("user", userByEmail);
        param.put("topic", topic);


        List mapp = new LinkedList();
        List<Comments> commentones = commentsService.getCommentOne(dId);
        List<Comments> commentOnes = commentsService.getCommentsIncludeName(commentones);

        for (Comments commentone : commentOnes) {
            List<Comments> commenttwos = commentsService.getCommentTwo(dId, commentone.getCommentId());
            List<Comments> commentTwos = commentsService.getCommentsIncludeName(commenttwos);
            Map map1 = new HashMap();
            List comments1 = new LinkedList();
            for (Comments commenttwo : commentTwos) {
                Map map2 = new HashMap();
                List map12 = new LinkedList();
                List<Comments> commentthree = commentsService.getCommentThree(dId, commentone.getCommentId(), commenttwo.getCommentId());
                List<Comments> commentThree = commentsService.getCommentsIncludeName(commentthree);
                for (Comments comment3:commentThree) {
                    Map map = new HashMap();
                    Comments commentByCId = commentsService.getCommentByCId(comment3.getReplyId());
                    String email1 = null;
                    try {
                        email1 = commentByCId.getEmail();
                        User userByEmail1 = userService.getUserByEmail(email1);
                        map.put("user",userByEmail1);

                    } catch (Exception e) {

                    }
                    map.put("comment",comment3);
                    map12.add( map);
                }
                map2.put("comments",map12);
                map2.put("content", commenttwo);
                comments1.add(map2);
            }
            map1.put("comments", comments1);
            map1.put("content", commentone);
            mapp.add( map1);
        }
        param.put("commentsAll", mapp);
        return param;
    }


    @GetMapping("dynamic/my")
    public Map myDynamic(String email , int pageNumber){
        Map map = new HashMap();
        List<Dynamic> myDynamic = dynamicService.getMyDynamic(email, pageNumber);
        showDynamic(myDynamic,map);
        return map;
    }

}

