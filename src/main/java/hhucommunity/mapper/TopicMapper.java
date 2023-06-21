package hhucommunity.mapper;

import hhucommunity.model.Topic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicMapper {
    @Insert("Insert into topic(title,description,gmt_creat,gmt_modified, creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void creat(Topic topic);

    @Select("Select * from topic limit #{offset},#{size}")
    List<Topic> list(int offset, Integer size);

    @Select("Select count(1) from topic")
    Integer count();
}
