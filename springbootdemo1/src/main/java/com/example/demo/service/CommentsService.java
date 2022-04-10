package com.example.demo.service;

import com.example.demo.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Chu
 * @since 2022-03-19
 */
@Service
public interface CommentsService extends IService<Comments> {
    int insertComment(Comments comments);
    List<Comments> selectComments(String email);
    List<Comments> selectCommentsByDid(int dId);
    List<Comments> selectCommentsByDidLimit(int dId , int pageNumber , int pageSize);
    List<Comments> getCommentsIncludeName(List<Comments> comments);
    void update(List<Comments> comments);
    List<Comments> getCommentOne(int dId);
    List<Comments> getCommentTwo(int dId , int commentOneId);
    List<Comments> getCommentThree(int dId , int commentOneId , int commentTwoId);

    /**
     * 通过评论id获得此条评论
     * @param cId
     * @return
     */
    Comments getCommentByCId(int cId);

}
