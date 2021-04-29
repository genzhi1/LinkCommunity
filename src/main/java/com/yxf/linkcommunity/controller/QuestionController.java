package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.service.CommentService;
import com.yxf.linkcommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;





    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){

        QuestionDto userByQuestion = questionService.getUserByQuestionId(id);
        questionService.IncViewCount(id);
        List<QuestionDto> questionDtoList= commentService.getCommentByQuestionId(id);
        model.addAttribute("id",id);
        model.addAttribute("questionWithUser",userByQuestion);
        return "question";



    }
}
