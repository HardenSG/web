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
    boolean queryLike(String email,int dId);
    int insertLike(Like like);
    void update(List<Like> comments);
}
