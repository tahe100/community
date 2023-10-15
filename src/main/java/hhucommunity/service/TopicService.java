package hhucommunity.service;

import hhucommunity.dto.PaginationDTO;
import hhucommunity.dto.TopicDTO;
import hhucommunity.exception.CustomizeErrorCode;
import hhucommunity.exception.CustomizeException;
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

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = topicMapper.count();

        int totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }


        if(page < 1 ){
            page = 1;
        }

        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        List<TopicDTO> topicDTOs= new ArrayList<>();

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

        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = topicMapper.countByUserId(userId);

        int totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }


        if(page < 1 ){
            page = 1;
        }

        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);

        List<TopicDTO> topicDTOs= new ArrayList<>();

        int offset = (page -1) * size;
        List<Topic> topics = topicMapper.listByUserId(userId,offset,size);

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

        return paginationDTO;

    }

    public TopicDTO getById(Integer id) {

        //TopicDTO topic = topicMapper.getById(id);不能这样写 因为存的表里的是topic 而不是topicDTO(topicDTO 还封装了user)
        Topic topic = topicMapper.getById(id);
        if(topic == null){
            throw new CustomizeException(CustomizeErrorCode.TOPIC_NOT_FOUND);
        }
        TopicDTO topicDTO =new TopicDTO();
        BeanUtils.copyProperties(topic,topicDTO);
        topicDTO.setCommunityUser(userMapper.findByID(topic.getCreator()));

        return  topicDTO;
    }

    public void cteateOrUpdate(Topic topic) {
        if(topic.getId() == null){
            //创建
            topic.setGmtCreat(System.currentTimeMillis());
            topic.setGmtModified(topic.getGmtModified());
            topicMapper.creat(topic);
        }else{
            //更新
            topic.setGmtModified(topic.getGmtModified());
            topicMapper.update(topic);
        }
    }
}
