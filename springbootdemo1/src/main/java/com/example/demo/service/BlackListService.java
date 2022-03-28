package com.example.demo.service;

import com.example.demo.entity.BlackList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-27
 */
public interface BlackListService extends IService<BlackList> {
    int insertBlackList(BlackList blackList);
    boolean isExist(BlackList blackList);
}
