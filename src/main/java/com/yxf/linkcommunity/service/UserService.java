package com.yxf.linkcommunity.service;

import com.yxf.linkcommunity.dto.GithubUser;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        if(users.size()!=0){
            //update
            User dbuser=users.get(0);
            User updataUser = new User();
            updataUser.setGmtModified(System.currentTimeMillis());
            updataUser.setName(user.getName());
            updataUser.setToken(user.getToken());
            updataUser.setAvatarUrl(user.getAvatarUrl());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updataUser,userExample);
        }else{
            //create
            userMapper.insert(user);
        }

    }


    }


