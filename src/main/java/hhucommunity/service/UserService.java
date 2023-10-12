package hhucommunity.service;

import hhucommunity.mapper.UserMapper;
import hhucommunity.model.CommunityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(CommunityUser communityUser) {
        CommunityUser dbUser = userMapper.findByAccountId(communityUser.getAccountId());
        if(dbUser == null){
            //插入
            communityUser.setGmtCreat(System.currentTimeMillis());
            communityUser.setGmtModified(communityUser.getGmtCreat());
            userMapper.insert(communityUser);
        }else{
            //更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(communityUser.getAvatarUrl());
            dbUser.setName(communityUser.getName());
            dbUser.setToken(communityUser.getToken());
            userMapper.update(dbUser);

        }

    }
}
