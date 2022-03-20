package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
@Insert("insert into user(name,password,email) VALUES(#{name},#{password},#{email})")
   int savaUser(@Param("name") String name,@Param("password") String password,@Param("email")String email);
@Select("SELECT email from user where email=#{email}")
   String checkUser(@Param("email")String email);

}
