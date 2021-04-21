package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.dto.PagenationDto;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          @RequestParam(value = "page", defaultValue = "2") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                          Model model) {


        User user= (User) request.getSession().getAttribute("githubUser");
        if(user==null){
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            PagenationDto pagenationDto = questionService.findQuestionByUser(user.getId(),page,size);
            model.addAttribute("questions",pagenationDto);

            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }
}
