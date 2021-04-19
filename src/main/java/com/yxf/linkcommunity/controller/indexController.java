package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class indexController {


    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;


    @GetMapping("/")
    public String IndexController(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie target:cookies)
        {
            if(target.getName().equals("token"))
            {
                String token=target.getValue();
                User user=userMapper.findByToken(token);
                if(user!=null)
                {
                    request.getSession().setAttribute("githubUser",user);
                }
                    break;
            }
        }

        List<QuestionDto> questionDtos=questionService.list();
        System.out.println(questionDtos);

        return "index";
    }
}
