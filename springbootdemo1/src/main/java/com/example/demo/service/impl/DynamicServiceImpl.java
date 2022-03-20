package com.example.demo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.Dynamic;
import com.example.demo.entity.User;
import com.example.demo.mapper.DynamicMapper;
import com.example.demo.service.DynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-03-19
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements DynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;

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
}
