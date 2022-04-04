package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Follow;
import com.example.demo.entity.Topic;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-24
 */

public interface FollowService extends IService<Follow> {
    int insertFollow(String uId,String followId);
    List getFollowByUserEmail(String userEmail);
    boolean isExist(Follow follow);
    int deleteFollow(Follow follow);

}
