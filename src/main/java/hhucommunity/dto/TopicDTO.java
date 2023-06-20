package hhucommunity.dto;

import hhucommunity.model.CommunityUser;
import lombok.Data;

@Data
public class TopicDTO {
    //在topic对象上加上user对象 来使index里topic显示时有用户头像
    //不在model里的topic里加对象因为topic本身是一个数据库模型 存topic时不需要存对象
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreat;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private  Integer viewCount;
    private  Integer likeCount;
    private  String tag;
    private CommunityUser communityUser;
}
