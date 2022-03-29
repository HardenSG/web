package com.example.demo.service;

import com.example.demo.entity.DynamicPicture;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-29
 */
public interface DynamicPictureService extends IService<DynamicPicture> {
    int insertDynamicPicture(DynamicPicture dynamicPicture);
}
