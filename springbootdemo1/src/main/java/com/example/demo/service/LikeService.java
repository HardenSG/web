package com.example.demo.service;

import com.example.demo.entity.Like;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chu
 * @since 2022-03-18
 */
public interface LikeService extends IService<Like> {
    boolean queryLike(String email,int dId);
    int insertLike(Like like);
}
