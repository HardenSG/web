package com.example.demo.service;

import com.example.demo.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chu
 * @since 2022-03-19
 */
public interface CommentsService extends IService<Comments> {
    int insertComment(Comments comments);
    List<Comments> selectComments(String email);
    List<Comments> selectCommentsByDid(int dId);
    List<Comments> selectCommentsByDidLimit(int dId , int pageNumber , int pageSize);
    List<Comments> getCommentsIncludeName(List<Comments> comments);
}
