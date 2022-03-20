package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public interface UserService extends IService<User> {
  boolean userRepeat(String email);
  public Map changeBy(User user, HttpServletRequest request);
  public  Map selectBy(HttpServletRequest request);

}
