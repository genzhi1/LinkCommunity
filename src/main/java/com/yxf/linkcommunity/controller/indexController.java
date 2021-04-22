package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.dto.PagenationDto;
import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
public class indexController {


    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;


    @GetMapping("/")
    public String IndexController(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "5") Integer size) {





        PagenationDto pagenationDto = questionService.list(page,size);

        model.addAttribute("questions", pagenationDto);


        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("githubUser");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);


        return "redirect:/";
    }

}
