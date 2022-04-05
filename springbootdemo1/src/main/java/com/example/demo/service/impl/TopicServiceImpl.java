package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Topic;
import com.example.demo.mapper.TopicMapper;
import com.example.demo.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author no
 * @since 2022-03-21
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {
    @Autowired
    TopicMapper topicMapper;
    private static long time = 1000*60*60*24;

    /**
     * 根据话题内容模糊搜索话题，并且热度+1
     * @param content
     * @return
     */
    @Override
    public List<Integer> searchTopicId(String content) {
        Date date = new Date(System.currentTimeMillis());
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.like("content",content);
        QueryWrapper<Topic> topicQueryWrapper2 = new QueryWrapper<>();
        topicQueryWrapper2.eq("content",content);
        List<Topic> topics = topicMapper.selectList(topicQueryWrapper);
        Topic topic2 = topicMapper.selectOne(topicQueryWrapper2);
        try {
            topic2.setHot(topic2.getHot()+1);
            topicMapper.update(topic2,topicQueryWrapper2);
            List dIds = new LinkedList();
            for (Topic topic : topics) {
                dIds.add(topic.getTId());
            }
            return dIds;
        } catch (Exception e) {
            return null;
        }

    }


    public Integer searchTopicIdNoHot(String content) {
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.eq("content",content);
        Topic topic = topicMapper.selectOne(topicQueryWrapper);
        try {
            return topic.getTId();
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 搜索话题
     * @param content
     * @return
     */
    public Topic searchTopic(String content) {
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.eq("content",content);
        Topic topic = topicMapper.selectOne(topicQueryWrapper);
        return topic;
    }

    @Override
    public List searchTopicList(String content) {
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.like("content",content)
                .orderByDesc("hot");
        List topic = topicMapper.selectList(topicQueryWrapper);
        return topic;
    }


    @Override
    public Integer insertTopic(Topic topic) {
        int insert = topicMapper.insert(topic);
        return insert;
    }

    @Override
    public String getTopic(Integer tId) {
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.eq("t_id", tId);
        Topic topic = topicMapper.selectOne(topicQueryWrapper);
        try {
            return topic.getContent();
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public List getTopicByHotPart() {
        Page<Topic> page = new Page<>(0,10);
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.orderByDesc("hot")
                .between("date", DateUtils.parseDate(new Date(System.currentTimeMillis())),DateUtils.parseDate(new Date(System.currentTimeMillis()+time)));
        Page<Topic> page1 = topicMapper.selectPage(page, topicQueryWrapper);
        return page1.getRecords();
    }

    @Override
    public List getTopicByHotAll() {
        Page<Topic> page = new Page<>(0,100);
        QueryWrapper<Topic> topicQueryWrapper = new QueryWrapper<>();
        topicQueryWrapper.orderByDesc("hot")
                .between("date", DateUtils.parseDate(new Date(System.currentTimeMillis())),DateUtils.parseDate(new Date(System.currentTimeMillis()+time)));
        Page<Topic> page1 = topicMapper.selectPage(page, topicQueryWrapper);
        return page1.getRecords();
    }


}
