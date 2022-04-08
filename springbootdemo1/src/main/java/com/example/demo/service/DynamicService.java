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

    /**
     * 更新动态字段（点赞数等）
     * @param column
     * @param val
     * @param dId
     * @return
     */
    int updateByColumn( String column, Object val,int dId);

    /**
     * 通过动态id获取动态
     * @param dId
     * @return
     */
    Dynamic getDynamic(int dId);

    /**
     * 添加动态
     * @param dynamic
     * @return
     */
    int insertDynamic(Dynamic dynamic);

    /**
     * 分页返回
     * @param pageNumber
     * @return
     */
    List pageList(int pageNumber);

    /**
     * 删除动态
     * @param request
     * @param did
     * @param email
     * @return
     */
    Map deleteDynamic(HttpServletRequest request,Integer did,String email);

    /**
     * 通过
     * @param follows
     * @param pageNumber
     * @return
     */
    List getDynamicByFollow(List<Follow> follows,Integer pageNumber);
    List<Integer> getDynamicIdByEmail(String email);
    List<Integer> getDynamicIdByEmailLike(String email);
    List<Integer> getDynamicIdByEmailComment(String email);
    List<Integer> getDynamicIdByEmailForward(String email);


    List commentNotice(HttpServletRequest request);
    List likeNotice(HttpServletRequest request);

    List forwardNotice(HttpServletRequest request);
    List getMyDynamic(String email , int pageNumber);
    //点赞数
    int likeCount(String email);
    //评论数
    int commentCount(String email);
    //转发数
    int forwardCount(String email);


    List getDynamicByTopic(List<Integer> tIds,int pageNumber);
}







