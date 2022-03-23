package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Like;
import com.example.demo.mapper.LikeMapper;
import com.example.demo.service.LikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Chu
 * @since 2022-03-18
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {

    @Autowired
    LikeMapper likeMapper;
    @Override
    public boolean queryLike(String email, int dId) {
        QueryWrapper<Like> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("d_id",dId)
                .eq("email",email);

        if(likeMapper.selectCount(queryWrapper) == 0) {
            return true;
        }else {
            return false;
        }

    }

    @Override
    public int insertLike(Like like) {
        int insert = likeMapper.insert(like);
        return insert;
    }
}
