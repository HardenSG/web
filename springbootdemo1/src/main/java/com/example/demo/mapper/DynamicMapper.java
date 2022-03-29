
package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.Dynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Chu
 * @since 2022-03-15
 */
@Mapper
@Repository
public interface DynamicMapper extends BaseMapper<Dynamic> {

     int insertDynamic(String email,String content, String picture, Date date);
}
