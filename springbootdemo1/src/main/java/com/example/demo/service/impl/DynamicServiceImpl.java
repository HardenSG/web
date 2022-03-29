package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.mapper.CommentsMapper;
import com.example.demo.mapper.DynamicMapper;
import com.example.demo.mapper.LikeMapper;
import com.example.demo.service.CommentsService;
import com.example.demo.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Dynamic;

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
 *
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
    private LikeMapper likeMapper;

    @Override
    public int insertDynamic1(Dynamic dynamic) {
        return dynamicMapper.insert(dynamic);
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


}
