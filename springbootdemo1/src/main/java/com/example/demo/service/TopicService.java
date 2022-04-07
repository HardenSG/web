package com.example.demo.service;

import com.example.demo.entity.Comments;
import com.example.demo.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author no
 * @since 2022-03-21
 */
public interface TopicService extends IService<Topic> {
    List<Integer> searchTopicId(String content);
    Integer insertTopic(Topic topic);
    String getTopic(Integer tId);
    List getTopicByHotPart();
    List getTopicByHotAll(Integer pageNumebr);
    Topic searchTopic(String content);
    List searchTopicList(String content);
    Integer searchTopicIdNoHot(String content);

}
