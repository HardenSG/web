package com.example.demo.service;

import com.example.demo.entity.Like;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chu
 * @since 2022-03-18
 */
@Service
public interface LikeService extends IService<Like> {
    /**
     * 此用户是否已经对该动态点赞
     * @param email
     * @param dId
     * @return 点过赞return false
     */
    boolean queryLike(String email,int dId);

    /**
     * 添加一条点赞记录
     * @param like
     * @return
     */
    int insertLike(Like like);


    void update(List<Like> comments);
}
