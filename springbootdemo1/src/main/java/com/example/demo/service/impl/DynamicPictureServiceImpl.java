package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.DynamicPicture;
import com.example.demo.mapper.DynamicPictureMapper;
import com.example.demo.service.DynamicPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List queryPicure(int dId) {
        QueryWrapper<DynamicPicture> queryWrapper = new QueryWrapper();
        queryWrapper.eq("d_id",dId);
        List<DynamicPicture> dynamicPictures = dynamicPictureMapper.selectList(queryWrapper);
        List pictureUrls = new LinkedList();
        for (DynamicPicture dynamicPicture : dynamicPictures) {
            pictureUrls.add(dynamicPicture.getPicture());
        }
        return pictureUrls;
    }
}
