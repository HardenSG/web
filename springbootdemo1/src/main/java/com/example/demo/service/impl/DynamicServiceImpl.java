package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.service.CommentsService;
import com.example.demo.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.service.ForwardService;
import com.example.demo.service.LikeService;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>
 *  服务实现类
 * </p>
 * @author admin
 * @since 2022-03-15
 */

@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements DynamicService {

   @Autowired
    private DynamicMapper dynamicMapper;
   @Autowired
   private CommentsMapper commentsMapper;
   @Autowired
   private UserMapper userMapper;
   @Autowired
   private LikeMapper likeMapper;
   @Autowired
   private LikeService likeService;
@Autowired
private DynamicPictureMapper dynamicPictureMapper;
@Autowired
private CommentsService commentsService;
@Autowired
private ForwardMapper forwardMapper;
@Autowired
private ForwardService forwardService;


    /**
     * 通过动态id更改某个字段
     * @param column 被更改的字段
     * @param val 字段被更改成的值
     * @param dId 动态id
     * @return
     */
    public int updateByColumn( String column, Object val,int dId) {
        UpdateWrapper<Dynamic> dynamicUpdateWrapper = new UpdateWrapper<>();
        dynamicUpdateWrapper.eq("d_id",dId);
        dynamicUpdateWrapper.set(column,val);
        int update = dynamicMapper.update(null, dynamicUpdateWrapper);
        return update;
    }

    /**
     * 通过id获得该动态
     * @param dId
     * @return
     */
    public Dynamic getDynamic(int dId){
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("d_id",dId);
        Dynamic dynamic = dynamicMapper.selectOne(dynamicQueryWrapper);
        return dynamic;
    }

    @Override
    public int insertDynamic(Dynamic dynamic) {
        int insert = dynamicMapper.insert(dynamic);
        return insert;
    }


    @Override
    public List pageList(int pageNumber) {
        Page<Dynamic> page = new Page<>(pageNumber,5);
        QueryWrapper<Dynamic> queryWrapper= new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        Page<Dynamic> dynamicPage = dynamicMapper.selectPage(page, queryWrapper);
        List<Dynamic> records = dynamicPage.getRecords();
        return records;
    }

    /**
     *
     * @param tId
     * @return
     */
    @Override
    public List getDynamicByTopic(int tId,int pageNumber) {
        Page<Dynamic> page = new Page<>(pageNumber,5);
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("t_id",tId);
        Page<Dynamic> dynamicPage = dynamicMapper.selectPage(page, dynamicQueryWrapper);
        List<Dynamic> records = dynamicPage.getRecords();
        return records;
    }


    //删除动态
    @Override
    public Map deleteDynamic(HttpServletRequest request, Integer did, String email) {
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emaillog= JwtUtils.parseEmail(token);
        if(email.equals(emaillog)){
            int numDynamic=dynamicMapper.delete(new QueryWrapper<Dynamic>().eq("d_id",did));
            int likeDynamic=likeMapper.delete(new QueryWrapper<Like>().eq("d_id",did));
            int commentDynamic=commentsMapper.delete(new QueryWrapper<Comments>().eq("d_id",did));
            int pictureDynamic=dynamicPictureMapper.delete(new QueryWrapper<DynamicPicture>().eq("d_id",did));

            if(numDynamic>0||likeDynamic>0||commentDynamic>0||pictureDynamic>0){
                param.put("status",200);
                param.put("msg","删除成功");
            }else{
                param.put("status",0);
                param.put("msg","删除失败");
            }
            UpdateWrapper<Dynamic> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("original_id", did);
            Dynamic dynamic = new Dynamic();
            dynamic.setOriginalId(-2);
            dynamicMapper.update(dynamic,updateWrapper);
        }
        return param;
    }


    /**
     *
     * @param follows
     * @param pageNumber
     * @return
     */
    @Override
    public List getDynamicByFollow(List<Follow> follows , Integer pageNumber) {
        Page<Dynamic> page = new Page<>(pageNumber,5);
        QueryWrapper<Dynamic> queryWrapper= new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        for (Follow follow : follows) {
            queryWrapper.eq("email",follow.getFollowedEmail());
        }
        Page<Dynamic> dynamicPage = dynamicMapper.selectPage(page, queryWrapper);
        List<Dynamic> records = dynamicPage.getRecords();
        return records;
    }

    /**
     * 通过用户邮箱获得全部动态id
     * @param email
     * @return
     */
    @Override
    public List<Integer> getDynamicIdByEmail(String email) {
        QueryWrapper<Dynamic> queryWrapper = new QueryWrapper();
        queryWrapper.eq("email",email);
        List<Dynamic> dynamics = dynamicMapper.selectList(queryWrapper);
        List dynamicIds = new LinkedList();
        for (Dynamic dynamic:dynamics) {
            dynamicIds.add(dynamic.getDId());
        }
        return dynamicIds;
    }

    public List getMyDynamic(String email , int pageNumber){
        Page<Dynamic> page = new Page<>(pageNumber,5);
        QueryWrapper<Dynamic> queryWrapper= new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        queryWrapper.eq("email",email);
        Page<Dynamic> dynamicPage = dynamicMapper.selectPage(page, queryWrapper);
        List<Dynamic> records = dynamicPage.getRecords();
        return records;
    }

    @Override
    public int noticeCount(String email) {
        int notices = 0;
        List<Integer> dIds = getDynamicIdByEmail(email);
        for (int dId : dIds) {
            QueryWrapper<Comments> queryWrapper = new QueryWrapper();
            queryWrapper.eq("comment_read",0)
                    .eq("d_id",dId);
            Integer comment = commentsMapper.selectCount(queryWrapper);
            QueryWrapper<Like> queryWrapper1= new QueryWrapper();
            queryWrapper1.eq("like_read",0)
                    .eq("d_id",dId);
            Integer like = likeMapper.selectCount(queryWrapper1);
            notices = notices+comment+like;
        }
        return notices;
    }
    

//评论通知
    @Override
    public Map commentNotice(HttpServletRequest request) {
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token = request.getHeader("token");
        String emailData = JwtUtils.parseEmail(token);

        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("email", emailData)
                .gt("comment_count", 0);
        //动态记录合集
        List<Dynamic> data = dynamicMapper.selectList(dynamicQueryWrapper);

        int j = 0;
        //通过动态表合集查到Comments里的符合条件的对象合集
        for (Dynamic did : data) {
            int i = 0;
            j++;

            Map info = new HashMap();
            Integer dId = did.getDId();
            List<Comments> comment = commentsMapper.selectList(new QueryWrapper<Comments>().eq("d_id", dId)
                                               .orderByDesc("comment_date")
                                               .eq("comment_read", 0)
            );
            //把这个comment的Read值变成1
            commentsService.update(comment);
            //如果有未读的才把这条动态放进map
            if (comment.size() > 0) {
                info.put("dynamic", did);
                //通过评论表中的email查到user表中的head_picture
                for (Comments comm : comment) {
                    i++;
                    HashMap<Object, Object> peoplecomment = new HashMap<>();
                    peoplecomment.put("comment", comm);
                    String email = comm.getEmail();
                    List user = userMapper.selectList(new QueryWrapper<User>().eq("email", email));
                    peoplecomment.put("user", user);
                    info.put("peopleComment" + i, peoplecomment);

                }
                param.put("info" + j, info);
            }
        }

            return param;
        }

//点赞通知
    @Override
    public Map likeNotice(HttpServletRequest request){
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emailData= JwtUtils.parseEmail(token);
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("email", emailData)
                           .gt("likes",0);
        //动态记录合集
        List<Dynamic> data = dynamicMapper.selectList(dynamicQueryWrapper);

        int j=0;

        //通过动态表合集查到Comments里的符合条件的对象合集
        for ( Dynamic did:data) {
            int i = 0;
            j++;
            Map info = new HashMap();
            info.put("dynamic", did);
            Integer dId = did.getDId();
            List <Like>comment = likeMapper.selectList(new QueryWrapper<Like>().eq("d_id", dId)
                    .orderByDesc("like_date")
                    .eq("like_read", 0)

            );
            likeService.update(comment);
            //如果有未读的才把这条动态放进map
            if (comment.size() > 0) {
            //通过like表中的email查到user表中的head_picture
            for (Like comm : comment) {
                i++;
                HashMap<Object, Object> peoplecomment = new HashMap<>();
                peoplecomment.put("like", comm);
                String email = comm.getEmail();
                List user = userMapper.selectList(new QueryWrapper<User>().eq("email", email));
                peoplecomment.put("user", user);
                info.put("peopleLike" + i, peoplecomment);
            }
            param.put("info" + j, info);
        }
        }

        return param;

    }



    //转发通知
    @Override
    public Map forwardNotice(HttpServletRequest request) {

        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emailData= JwtUtils.parseEmail(token);
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("email", emailData)
                           .gt("forward_count",0);
        //动态记录合集
        List<Dynamic> data = dynamicMapper.selectList(dynamicQueryWrapper);
        //  HashMap<Object, Object> peoplecomment=null;
        // dynamic=null;
        int j=0;

        //通过动态表合集查到forward里的符合条件的对象合集
        for ( Dynamic did:data) {
            int i = 0;
            j++;
            Map info = new HashMap();
            info.put("dynamic", did);
            Integer dId = did.getDId();
            List<Forward> comment = forwardMapper.selectList(new QueryWrapper<Forward>().eq("d_id", dId)
                    .orderByDesc("forward_date")
            );
            forwardService.update(comment);
            if (comment.size() > 0) {
                //通过forward表中的email查到user表中的head_picture
                for (Forward comm : comment) {
                    i++;
                    HashMap<Object, Object> peoplecomment = new HashMap<>();
                    peoplecomment.put("forward", comm);
                    String email = comm.getEmail();
                    List user = userMapper.selectList(new QueryWrapper<User>().eq("email", email));
                    peoplecomment.put("user", user);
                    info.put("peopleForward" + i, peoplecomment);
                }
                param.put("info" + j, info);
            }
        }

        return param;

    }
}
