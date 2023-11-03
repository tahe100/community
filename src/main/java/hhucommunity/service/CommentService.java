package hhucommunity.service;

import hhucommunity.dto.CommentInTopicDTO;
import hhucommunity.enums.CommentTypeEnum;
import hhucommunity.exception.CustomizeErrorCode;
import hhucommunity.exception.CustomizeException;
import hhucommunity.mapper.CommentMapper;
import hhucommunity.mapper.TopicMapper;
import hhucommunity.mapper.UserMapper;
import hhucommunity.model.Comment;
import hhucommunity.model.CommunityUser;
import hhucommunity.model.Topic;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TopicService topicService;


    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TOPIC_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Topic topic = topicMapper.getById(comment.getParentId());
            if(topic == null){
                throw new CustomizeException(CustomizeErrorCode.TOPIC_NOT_FOUND);
            }
            commentMapper.insert(comment);

            topicService.incComment(topic.getId());
        }

    }

    public List<CommentInTopicDTO> listByTopicId(Integer id) {
        List<Comment> comments = commentMapper.listByTopicId(id);

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());
        List<Integer>  userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转化为map
        Map<Integer, CommunityUser> userMap = new HashMap<>();
        for(Integer userId : userIds){
            CommunityUser user = userMapper.findByID(userId);
            userMap.put(userId,user);
        }


        //转化comment为CommentInTopicDTO
        List<CommentInTopicDTO> CommentInTopicDTOs = comments.stream().map(comment -> {
            CommentInTopicDTO commentInTopicDTO = new CommentInTopicDTO();
            BeanUtils.copyProperties(comment, commentInTopicDTO);
            commentInTopicDTO.setCommunityUser(userMap.get(comment.getCommentator()));
            return commentInTopicDTO;
        }).toList();




        return CommentInTopicDTOs;
    }
}
