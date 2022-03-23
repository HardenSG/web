package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    User getUserByEmailAndPassword(@Param("email") String email,@Param("password") String password);
    User getUserByEmail(@Param("email") String email);

}
