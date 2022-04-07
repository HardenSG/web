package com.example.demo.service.impl;

import com.example.demo.entity.Forward;
import com.example.demo.mapper.ForwardMapper;
import com.example.demo.service.ForwardService;
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
 * @since 2022-04-04
 */
@Service
public class ForwardServiceImpl extends ServiceImpl<ForwardMapper, Forward> implements ForwardService {
@Autowired
private ForwardMapper forwardMapper;
    @Override
    public void update(List<Forward> comments) {
        for (Forward comment: comments) {
            comment.setForwardRead(1);
            forwardMapper.updateById(comment);
        }
    }

    @Override
    public int insert(Forward forward) {
        int insert = forwardMapper.insert(forward);
        return insert;
    }

}
