package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.Dynamic;
import com.example.demo.mapper.DynamicMapper;
import com.example.demo.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Chu
 * @since 2022-03-15
 */

@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements DynamicService {

   @Autowired
    private DynamicMapper dynamicMapper;
    @Override
    public int insertDynamic(String email, String content ,String picture, Date date) {
        return dynamicMapper.insertDynamic(email,content,picture,date);
    }

    @Override
    public int insertDynamic1(Dynamic dynamic) {
        return dynamicMapper.insertDynamic1(dynamic);
    }
        // 根据条件更新
    public int updateByColumn( String column, Object val,int dId) {
        UpdateWrapper<Dynamic> dynamicUpdateWrapper = new UpdateWrapper<>();
        dynamicUpdateWrapper.eq("d_id",dId);
        dynamicUpdateWrapper.set(column,val);
        int update = dynamicMapper.update(null, dynamicUpdateWrapper);
        return update;
    }





//    // 根据条件更新
//    public void changeBy(User user, String column, Object val) {
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq(column, val);
//        int num = userMapper.update(user, userQueryWrapper);
//        System.out.println("影响行数：" + num);
//    }

//    // 通过ID修改信息
//    public void changeUserById(User user) {
//        int num = userMapper.updateById(user);
//        System.out.println("影响行数：" + num);
//    }
}
