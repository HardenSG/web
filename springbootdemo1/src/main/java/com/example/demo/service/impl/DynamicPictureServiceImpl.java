package com.example.demo.service.impl;

import com.example.demo.entity.DynamicPicture;
import com.example.demo.mapper.DynamicPictureMapper;
import com.example.demo.service.DynamicPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-29
 */
@Service
public class DynamicPictureServiceImpl extends ServiceImpl<DynamicPictureMapper, DynamicPicture> implements DynamicPictureService {
    @Autowired
    DynamicPictureMapper dynamicPictureMapper;
    @Override
    public int insertDynamicPicture(DynamicPicture dynamicPicture) {
        return dynamicPictureMapper.insert(dynamicPicture);
    }
}
