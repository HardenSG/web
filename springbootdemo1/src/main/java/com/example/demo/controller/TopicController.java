package com.example.demo.controller;


import com.example.demo.entity.Dynamic;
import com.example.demo.entity.Topic;
import com.example.demo.service.DynamicService;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
    @Autowired
    DynamicController dynamicController;
    /**
     * 通过话题得到动态内容
     * @param topic
     * @return
     */
    @PostMapping("/topic")
    public Map getDynamicByTopic(String topic , int pageNumber){
        Map param = new HashMap();
        List topic1 = topicService.searchTopicList(topic);
        List<Integer> list = topicService.searchTopicId(topic);
        if(list.size()==0){
            param.put("status",200);
            param.put("msg","暂无此话题");
            return param;
        }else{
            List dynamicByTopic = dynamicService.getDynamicByTopic(list , pageNumber);
            dynamicController.showDynamic(dynamicByTopic,param);
            param.put("topic",topic1.get(0));
        }
        return param;
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

