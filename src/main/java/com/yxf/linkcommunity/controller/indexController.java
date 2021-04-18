package com.yxf.linkcommunity.controller;

import com.yxf.mapper.UserMapper;
import com.yxf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class indexController {


    @Autowired(required = false)
    private UserMapper userMapper;



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

        return "index";
    }
}
