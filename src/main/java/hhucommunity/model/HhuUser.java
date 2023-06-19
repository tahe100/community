package hhucommunity.model;

import lombok.Data;

@Data
public class HhuUser {//dto是网络中传输中的object，在数据库中我们用model
    //在数据库表里用的是account_id而这里用驼峰表达式
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreat;
    private Long gmtModified;
    private String avatarUrl;
}
