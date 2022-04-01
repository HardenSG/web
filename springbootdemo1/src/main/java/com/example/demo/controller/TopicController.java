package com.example.demo.controller;


import com.example.demo.entity.Comments;
import com.example.demo.entity.Dynamic;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.CommentsService;
import com.example.demo.service.DynamicPictureService;
import com.example.demo.service.DynamicService;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author no
 * @since 2022-03-21
 */
@RestController
public class TopicController {
    @Autowired
    TopicService topicService;
    @Autowired
    DynamicService dynamicService;
    DynamicController dynamicController = new DynamicController();
    @Autowired
    UserMapper userService;
    @Autowired
    DynamicPictureService dynamicPictureService;
    @Autowired
    CommentsService commentsService;

    /**
     * 通过话题得到动态内容
     * @param topic
     * @return
     */
    @PostMapping("/topic")
    public Map getDynamicByTopic(String topic , int pageNumber){
        Map map = new HashMap();
        Integer tId = topicService.searchTopicId(topic);
        if(tId==null){
            map.put("status",200);
            map.put("msg","暂无此话题");
            return map;
        }
        List<Dynamic> list = dynamicService.getDynamicByTopic(tId,pageNumber);
        if(list.size()<5){
            map.put("message","已经到底啦！");
        }else {
            map.put("message","成功");
        }
        int i = 0;
        for (Dynamic dynamic : list) {
            Map param = new HashMap();
            i++;
            //拿到发动态的人的email
            String email = dynamic.getEmail();
            //拿到发此条动态的user
            User userByEmail = userService.getUserByEmail(email);
            //拿到至多5条评论
            List<Comments> comments = commentsService.getCommentsIncludeName(commentsService.selectCommentsByDidLimit(dynamic.getDId(),0,5));
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
            param.put("comments",comments);
            param.put("topic",topic);
            map.put("info"+i,param);
        }
        map.put("status",200);
        return map;
    }

    /**
     * 热搜榜
     * @return
     */
    @GetMapping("/topic")
    public Map getDynamicByHotPart(){
        Map param = new HashMap();
        List topicByHot = topicService.getTopicByHotPart();
        param.put("topics",topicByHot);
        param.put("status",200);
        param.put("msg","成功");
        return param;
    }

    /**
     * 全部热搜
     * @return
     */
    @GetMapping("/topicAll")
    public Map getDynamicByHotAll(){
        Map param = new HashMap();
        List topicByHot = topicService.getTopicByHotAll();
        param.put("topics",topicByHot);
        param.put("status",200);
        param.put("msg","成功");
        return param;
    }
}

