package hhucommunity.mapper;

import hhucommunity.dto.TopicDTO;
import hhucommunity.model.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TopicMapper {
    @Insert("Insert into topic(title,description,gmt_creat,gmt_modified, creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void creat(Topic topic);

    @Select("Select * from topic limit #{offset},#{size}")
    List<Topic> list(int offset, Integer size);

    @Select("Select count(1) from topic")
    Integer count();

    @Select("Select * from topic where creator = #{userId} limit #{offset},#{size}")
    List<Topic> listByUserId(@Param("userId") Integer userId, @Param("offset") int offset, @Param("size") Integer size);


    @Select("Select count(1) from topic where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("SELECT * from topic where id = #{id}")
    Topic getById(@Param("id")Integer id);

    @Update("update TOPIC set TITLE=#{title},DESCRIPTION=#{description},GMT_MODIFIED=#{gmtModified},TAG=#{tag} where ID=#{id}")
    void update(Topic topic);
}
