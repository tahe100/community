package hhucommunity.dto;

import hhucommunity.model.CommunityUser;
import lombok.Data;

@Data
public class CommentInTopicDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private CommunityUser communityUser;

}
