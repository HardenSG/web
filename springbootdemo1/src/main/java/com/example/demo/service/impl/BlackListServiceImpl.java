package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.BlackList;
import com.example.demo.mapper.BlackListMapper;
import com.example.demo.service.BlackListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-27
 */
@Service
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackList> implements BlackListService {
    @Autowired
    private BlackListMapper blackListMapper;
    @Override
    public int insertBlackList(BlackList blackList) {
        return blackListMapper.insert(blackList);
    }

    @Override
    public boolean isExist(BlackList blackList) {
        QueryWrapper<BlackList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_email",blackList.getUEmail())
                .eq("black_email",blackList.getBlackEmail());
        BlackList blackList1 = blackListMapper.selectOne(queryWrapper);
        if(blackList1!=null){
            return true;
        }else {
            return false;
        }
    }
}
