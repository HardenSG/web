package com.example.demo.service;

import com.example.demo.entity.Dynamic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Follow;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.sql.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-15
 */
public interface DynamicService extends IService<Dynamic> {
    int insertDynamic(String email, String content ,String picture, Date date);
    int insertDynamic1(Dynamic dynamic);
    int updateByColumn( String column, Object val,int dId);
    Dynamic getDynamic(int dId);
    int insertDynamic(Dynamic dynamic);
    List pageList(int pageNumber);
    List getDynamicByTopic(int tId);
    Map deleteDynamic(HttpServletRequest request,Integer did,String email);
    List getDynamicByFollow(List<Follow> follows,Integer pageNumber);

  Map commentNotice(HttpServletRequest request);

  Map likeNotice(HttpServletRequest request);
}







