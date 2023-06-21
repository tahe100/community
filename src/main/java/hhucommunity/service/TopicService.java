package hhucommunity.service;

import hhucommunity.dto.PaginationDTO;
import hhucommunity.dto.TopicDTO;
import hhucommunity.mapper.TopicMapper;
import hhucommunity.mapper.UserMapper;
import hhucommunity.model.CommunityUser;
import hhucommunity.model.Topic;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//不仅使用topicMapper还使用userMapper
//当一个请求需要组装两个表 用中间层service
public class TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        List<TopicDTO> topicDTOs= new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        int offset = (page -1) * size;
        List<Topic> topics = topicMapper.list(offset,size);

        for(Topic topic: topics){
            //找到user并组装
            //userMapper.findByID(topic.getCreator())在数据库里是下划线而在类里是驼峰所以在yml里要mybatis.configuration.map-underscore-to-camel-case=true
            CommunityUser user = userMapper.findByID(topic.getCreator());
            TopicDTO topicDTO = new TopicDTO();
            if(user != null){
                BeanUtils.copyProperties(topic,topicDTO);
            }

            topicDTO.setCommunityUser(user);
            topicDTOs.add(topicDTO);
        }

        paginationDTO.setTopics(topicDTOs);
        Integer totalCount = topicMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }
}
