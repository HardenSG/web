package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Comments;
import com.example.demo.entity.User;
import com.example.demo.mapper.CommentsMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Chu
 * @since 2022-03-19
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加评论
     * @param comments
     * @return
     */
    public int insertComment(Comments comments){
        int insert = commentsMapper.insert(comments);
        return insert;
    }
    //通过email查到comments里的记录
    @Override
    public List<Comments> selectComments(String email) {
        QueryWrapper<Comments> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("email",email);
        List<Comments> comments = commentsMapper.selectList(queryWrapper);
        return comments;
    }
    //通过did查comments里的记录
    @Override
    public List<Comments> selectCommentsByDid(int dId) {
        QueryWrapper<Comments> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("d_id",dId);
        List<Comments> comments = commentsMapper.selectList(queryWrapper);
        return comments;
    }
    //通过did查comments里的一级评论
    @Override
    public List<Comments> getCommentOne(int dId) {
        QueryWrapper<Comments> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("d_id",dId)
                .eq("parent_id",0)
                .eq("reply_id",0);
        List<Comments> comments = commentsMapper.selectList(queryWrapper);
        return comments;
    }

    //通过did查二级评论
    @Override
    public List<Comments> getCommentTwo(int dId,int commentOneId){
        QueryWrapper<Comments> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("d_id",dId)
                .eq("parent_id",commentOneId)
                .eq("reply_id",commentOneId);
        List<Comments> comments = commentsMapper.selectList(queryWrapper);
        return comments;
    }

    @Override
    public List<Comments> getCommentThree(int dId, int commentOneId, int commentTwoId) {
        QueryWrapper<Comments> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("d_id",dId)
                .eq("parent_id",commentOneId)
                .eq("reply_id",commentTwoId);
        List<Comments> comments = commentsMapper.selectList(queryWrapper);
        return comments;
    }



    //
    @Override
    public List<Comments> selectCommentsByDidLimit(int dId , int pageNumber , int pageSize) {
        Page<Comments> page= new Page<>(pageNumber,pageSize);
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("d_id",dId);
        List<Comments> records = commentsMapper.selectPage(page, queryWrapper).getRecords();
        return records;
    }
    //查到的评论表记录得到email，在去查user表里的name,head_picture
    @Override
    public List<Comments> getCommentsIncludeName(List<Comments> comments){
        List<Comments> commentsList = new LinkedList<>();
        for (Comments comment : comments) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("name", "head_picture").eq("email",comment.getEmail());
            User user = userMapper.selectOne(queryWrapper);
            comment.setName(user.getName());
            comment.setHeadPhoto(user.getHeadPicture());
            commentsList.add(comment);
        }
        return commentsList;
    }



}
