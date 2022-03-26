package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Comments;
import com.example.demo.entity.Dynamic;
import com.example.demo.entity.Like;
import com.example.demo.mapper.CommentsMapper;
import com.example.demo.mapper.DynamicMapper;
import com.example.demo.mapper.LikeMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;
import java.sql.Date;
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
    @Override
    public int insertDynamic(String email, String content ,String picture, Date date) {
        return dynamicMapper.insertDynamic(email,content,picture,date);
    }

    @Override
    public int insertDynamic1(Dynamic dynamic) {
        return dynamicMapper.insertDynamic1(dynamic);
    }

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

    @Override
    public List getDynamicByTopic(int tId) {
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("t_id",tId);
        List<Dynamic> dynamics = dynamicMapper.selectList(dynamicQueryWrapper);
        return dynamics;
    }

    @Override
    public Map deleteDynamic(HttpServletRequest request, Integer did, String email) {
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emaillog= JwtUtils.parseEmail(token);
        if(email.equals(emaillog)){
            int num=dynamicMapper.delete(new QueryWrapper<Dynamic>().eq("d_id",did));
            if(num>0){
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
//    //根据条件更新
//    public void changeBy(User user, String column, Object val) {
//        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq(column, val);
//        int num = userMapper.update(user, userQueryWrapper);
//        System.out.println("影响行数：" + num);
//    }

//    //通过ID修改信息
//    public void changeUserById(User user) {
//        int num = userMapper.updateById(user);
//        System.out.println("影响行数：" + num);
//    }

    @Override
    public Map commentNotice(HttpServletRequest request) {
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emailData= JwtUtils.parseEmail(token);


        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("email", emailData)
                .gt("comment_count",0);
        //动态记录合集
        List<Dynamic> data = dynamicMapper.selectList(dynamicQueryWrapper);
        //  HashMap<Object, Object> peoplecomment=null;
//         dynamic=null;
        int j=0;
        //通过动态表合集查到Comments里的符合条件的对象合集
        for ( Dynamic did:data){
            int i=0;
            j++;
            Map  info=new HashMap();
            info.put("dynamic",did);
            Integer dId =did.getDId();
            List<Comments> comment= commentsMapper.selectList(new QueryWrapper<Comments>().eq("d_id", dId)
                                                                   .orderByDesc("comment_date")
            );
            //通过评论表中的email查到user表中的head_picture
            for(Comments comm:comment){
                i++;
                HashMap<Object, Object>  peoplecomment= new HashMap<>();
                peoplecomment.put("comment",comm);
                String email = comm.getEmail();
                List user=userMapper.selectList(new QueryWrapper<User>().eq("email",email));
                peoplecomment.put("user",user);
                info.put("peopleComment"+i,peoplecomment);
            }
            param.put("info"+j,info);
        }
       //设置成1
        UpdateWrapper<Comments> updateWrapper = new UpdateWrapper<>();
        Comments comments = new Comments();
        comments.setCommentRead(1);
        commentsMapper.update(comments,updateWrapper);
        return param;
    }

    @Override
    public Map likeNotice(HttpServletRequest request) {

        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emailData= JwtUtils.parseEmail(token);
        QueryWrapper<Dynamic> dynamicQueryWrapper = new QueryWrapper<>();
        dynamicQueryWrapper.eq("email", emailData)
                           .gt("likes",0);
        //动态记录合集
        List<Dynamic> data = dynamicMapper.selectList(dynamicQueryWrapper);
        //  HashMap<Object, Object> peoplecomment=null;
        // dynamic=null;
        int j=0;

        //通过动态表合集查到Comments里的符合条件的对象合集
        for ( Dynamic did:data){
            int i=0;
            j++;
            Map  info=new HashMap();
            info.put("dynamic",did);
            Integer dId =did.getDId();
            List<Like> comment= likeMapper.selectList(new QueryWrapper<Like>().eq("d_id", dId)
                    .orderByDesc("like_date")
            );
            //通过like表中的email查到user表中的head_picture
            for(Like comm:comment){
                i++;
                HashMap<Object, Object>  peoplecomment= new HashMap<>();
                peoplecomment.put("like",comm);
                String email = comm.getEmail();
                List user=userMapper.selectList(new QueryWrapper<User>().eq("email",email));
                peoplecomment.put("user",user);
                info.put("peopleLike"+i,peoplecomment);
            }
            param.put("info"+j,info);
        }
        //设置成1
        UpdateWrapper<Like> updateWrapper = new UpdateWrapper<>();
        Like comments = new Like();
        comments.setLikeRead(1);
        likeMapper.update(comments,updateWrapper);
        return param;

    }
}
