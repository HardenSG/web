package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Dynamic;
import com.example.demo.mapper.DynamicMapper;
import com.example.demo.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

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

    /**
     * 通过动态id更改某个字段
     * @param column 被更改的字段
     * @param val 字段被更改成的值
     * @param dId 动态id
     * @return
     */
    public int updateByColumn( String column, Object val,int dId) {
        UpdateWrapper<Dynamic> dynamicUpdateWrapper = new UpdateWrapper<>();
        dynamicUpdateWrapper.eq("d_id",dId);
        dynamicUpdateWrapper.set(column,val);
        int update = dynamicMapper.update(null, dynamicUpdateWrapper);
        return update;
    }

    /**
     * 通过id获得该动态
     * @param dId
     * @return
     */
    public Dynamic getDynamic(int dId){
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("d_id",dId);
        Dynamic dynamic = dynamicMapper.selectOne(dynamicQueryWrapper);
        return dynamic;
    }

    @Override
    public int insertDynamic(Dynamic dynamic) {
        int insert = dynamicMapper.insert(dynamic);
        return insert;
    }

    @Override
    public List pageList(int pageNumber) {
        Page<Dynamic> page = new Page<>(pageNumber,5);
        QueryWrapper<Dynamic> queryWrapper= new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        Page<Dynamic> dynamicPage = dynamicMapper.selectPage(page, queryWrapper);
        List<Dynamic> records = dynamicPage.getRecords();
        return records;
    }

    @Override
    public List getDynamicByTopic(int tId) {
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("t_id",tId);
        List<Dynamic> dynamics = dynamicMapper.selectList(dynamicQueryWrapper);

        return dynamics;
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
