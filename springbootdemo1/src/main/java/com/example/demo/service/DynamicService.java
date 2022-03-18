package com.example.demo.service;

import com.example.demo.entity.Dynamic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chu
 * @since 2022-03-15
 */
public interface DynamicService extends IService<Dynamic> {
    int insertDynamic(String email, String content ,String picture, Date date);
    int insertDynamic1(Dynamic dynamic);
    int updateByColumn( String column, Object val,int dId);
}
