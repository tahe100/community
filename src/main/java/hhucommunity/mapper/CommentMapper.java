package hhucommunity.mapper;

import hhucommunity.dto.CommentInTopicDTO;
import hhucommunity.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO COMMENT (ID,PARENT_ID,TYPE,COMMENTATOR,GMT_CREATE,GMT_MODIFIED,LIKE_COUNT,CONTENT) VALUES (#{id},#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    public void insert(Comment comment);

    @Select("SELECT * from COMMENT where parent_id = #{parentId}")
    Comment selectByKey(Integer parentId);

    @Select("SELECT * from COMMENT where parent_id = #{id} AND TYPE = 1")
    List<Comment> listByTopicId(Integer id);
}
