package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Service
public interface UserService extends IService<User> {

    User getUserByEmailAndPassword(String email,String password);
    User getUserByEmail(String email);
}
