package com.example.demo.service;

import com.example.demo.entity.Dynamic;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-19
 */
public interface DynamicService extends IService<Dynamic> {
  public Map deleteDynamic(HttpServletRequest request,Integer did,String email);


}
