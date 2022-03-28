package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.service.*;
import com.example.demo.entity.Comments;
import com.example.demo.entity.Dynamic;
import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.service.DynamicService;
import com.example.demo.service.FollowService;
import com.example.demo.service.TopicService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-03-24
 */
@RestController
public class FollowController {
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private BlackListService blackListService;


    /**
     * 添加关注
     * @param request
     * @param email
     * @return
     */
    @GetMapping("/follow")
    public Map addFollow(HttpServletRequest request,String email){
        Map param = new HashMap();
        //得到email
        String userEmail = JwtUtils.parseEmail( request.getHeader("token"));
        BlackList blackList = new BlackList(email, userEmail);
        if(blackListService.isExist(blackList)){
            param.put("status","200");
            param.put("msg","您暂时无法关注");
            return param;
        }
        int i = followService.insertFollow(userEmail,email);
        if(i==1){
            param.put("status",200);
            param.put("msg","关注成功");
        }else {
            param.put("status",0);
            param.put("msg","关注失败");
        }
        return param;
    }
<<<<<<< HEAD

=======
>>>>>>> 8f84dd0ad97cfbb19bb5e9c96ddc09e75e206599
}

