package com.example.demo.controller;


import com.example.demo.entity.BlackList;
import com.example.demo.entity.Follow;
import com.example.demo.service.BlackListService;
import com.example.demo.service.FollowService;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-03-27
 */
@RestController
public class BlackListController {
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private FollowService followService;

    @RequestMapping("/blackList")
    public Map addBlackList(HttpServletRequest httpServletRequest , String email){
        //用户email
        String userEmail = JwtUtils.parseEmail( httpServletRequest.getHeader("token"));
        BlackList blackList = new BlackList(userEmail, email);
        Follow follow = new Follow(userEmail, email);
        Map param = new HashMap();
        //查看是否存在关注关系，若存在则删除
        if (followService.isExist(follow)){
            followService.deleteFollow(follow);
            int i = blackListService.insertBlackList(blackList);
            param.put("msg","已解除对该用户的关注");
            param.put("status",200);
            return param;
        }
        if(blackListService.isExist(blackList)){
            param.put("msg","该用户已在黑名单中");
            param.put("status",0);
            return param;
        }
        int i = blackListService.insertBlackList(blackList);

        if(i==0){
            param.put("msg","添加失败");
            param.put("status",0);
        }else{
            param.put("msg","添加成功");
            param.put("status",200);
        }
        return param;
    }

}

