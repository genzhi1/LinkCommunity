package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.dto.AccessTokenDto;
import com.yxf.linkcommunity.dto.GithubUser;
import com.yxf.linkcommunity.provider.GithubProvider;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirectUri}")
    private String redirectUri;

    @Autowired(required = false)
    private UserMapper userMapper;


    @Autowired
    private UserService userService;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);



        if(githubUser!=null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());

            userService.createOrUpdate(user);
            Cookie cookie = new Cookie("token",user.getToken());
            response.addCookie(cookie);
            //request.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }
        else
            return "redirect:/";
    }
}
