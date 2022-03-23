package com.example.demo.mapper;

import com.example.demo.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author no
 * @since 2022-03-21
 */
@Repository
@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

}
