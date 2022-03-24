package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Follow;
import com.example.demo.mapper.FollowMapper;
import com.example.demo.service.FollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-24
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
    @Autowired
    private FollowMapper followMapper;
    @Override
    public int insertFollow(String uId, String followId) {
        return followMapper.insert(new Follow(uId,followId));
    }

    @Override
    public List getFollowByUserEmail(String userEmail) {
        QueryWrapper<Follow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_email",userEmail);
        List<Follow> follows = followMapper.selectList(queryWrapper);
        return follows;
    }

}
