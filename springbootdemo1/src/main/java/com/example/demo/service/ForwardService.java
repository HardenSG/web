package com.example.demo.service;

import com.example.demo.entity.Forward;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-04-04
 */
@Service
public interface ForwardService extends IService<Forward> {
    void update(List<Forward> comments);
    int insert(Forward forward);
}
