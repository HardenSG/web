package com.example.demo.controller;


import com.example.demo.mapper.DynamicMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.DynamicService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-03-19
 */
@Slf4j
@RestController
@RequestMapping("dynamic")
@ResponseBody
public class DynamicController{
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private DynamicService dynamicService;
    //删除动态
    @DeleteMapping("delete")
    public Map deleteDynamic(HttpServletRequest request, Integer did, String email){
        return dynamicService.deleteDynamic(request,did,email);

    }
}

