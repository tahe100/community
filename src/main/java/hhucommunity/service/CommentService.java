package hhucommunity.service;

import hhucommunity.enums.CommentTypeEnum;
import hhucommunity.exception.CustomizeErrorCode;
import hhucommunity.exception.CustomizeException;
import hhucommunity.mapper.CommentMapper;
import hhucommunity.mapper.TopicMapper;
import hhucommunity.model.Comment;
import hhucommunity.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    TopicMapper topicMapper;

    @Autowired
    TopicService topicService;

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
}
