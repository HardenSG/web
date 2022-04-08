package com.example.demo.mapper;

import com.example.demo.entity.Comments;
import com.example.demo.entity.Like;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Chu
 * @since 2022-03-18
 */
@Repository
public interface LikeMapper extends BaseMapper<Like> {

}
