package hhucommunity.model;

import lombok.Data;

@Data
public class Topic {
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
}
