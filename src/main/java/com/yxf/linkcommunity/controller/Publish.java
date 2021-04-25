package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.mapper.QuestionMapper;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.Question;
import com.yxf.linkcommunity.model.QuestionExample;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Publish {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String publish(@PathVariable("id") Integer id,
                          Model model
                          ){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> questionList = questionMapper.selectByExample(questionExample);
        Question question=new Question();
        if(questionList.size()!=0)
        {
            question=questionList.get(0);
            model.addAttribute("tag",question.getTag());
            model.addAttribute("title",question.getTitle());
            model.addAttribute("description",question.getDescription());
            model.addAttribute("id",id);
        }

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@Param("title") String title,
                            @Param("description") String description,
                            @Param("tag") String tag,
                            @Param("id") Integer id,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("tag",tag);
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("id",id);



        if(title==null||title==""){
            model.addAttribute("error","title can not be null");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("error","description can not be null");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("error","tag can not be null");
            return "publish";
        }


        User user= (User) request.getSession().getAttribute("githubUser");
        if(user==null)
        {
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);

        questionService.createOrUpdateQuestion(question);
        return "redirect:/";

    }
}
