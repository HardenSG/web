package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-12
 */
@Service
@ResponseBody
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
   private UserMapper usermapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByEmailAndPassword(String email,String password) {

        return userMapper.getUserByEmailAndPassword(email,password);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    //判断注册的email是否重复
    @Override
    public boolean userRepeat(String email){
        System.out.println("----------"+usermapper.checkUser(email));
        if(usermapper.checkUser(email)==null){
            return true;
        }else {
            return false;
        }
    }
    @Override
    //通过email修改name,birthday,introduction,sex,head_picture
    public Map changeBy(User user,HttpServletRequest request) {
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
         String token=request.getHeader("token");
          String emailData= JwtUtils.parseEmail(token);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("email", emailData);
        int num = usermapper.update(user,updateWrapper);
        if(num>0){
            param.put("status","200");
            param.put("msg","修改成功");

        }else{
            param.put("msg","修改失败");
        }
        return param;
    }
    @Override
    //通过email查询name,birthday,introduction,sex,head_picture
    public  Map selectBy(HttpServletRequest request) {
        HashMap<Object, Object> param = new HashMap<>();
        //获取email
        String token=request.getHeader("token");
        String emailData= JwtUtils.parseEmail(token);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", emailData);
        List data = usermapper.selectList(userQueryWrapper);
        if(data==null){
            param.put("msg","此用户不存在");

        }else{

            param.put("status","200");
            param.put("msg","查询成功");
            param.put("List",data);
        }
        return param;
    }
    //通过email查询name,birthday,introduction,sex,head_picture
    public  Map selectByemail(String email) {
        HashMap<Object, Object> param = new HashMap<>();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);
        List data = usermapper.selectList(userQueryWrapper);
        if(data.size()==0){
            param.put("msg","此用户不存在");

        }else{

            param.put("status","200");
            param.put("msg","查询成功");
            param.put("List",data);
        }
        return param;
    }


}
