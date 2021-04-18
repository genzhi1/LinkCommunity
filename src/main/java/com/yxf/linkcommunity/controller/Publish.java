package com.yxf.linkcommunity.controller;

import com.yxf.mapper.QuestionMapper;
import com.yxf.mapper.UserMapper;
import com.yxf.model.Question;
import com.yxf.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/publish")
public class Publish {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping
    public String publish(){
        return "publish";
    }

    @PostMapping
    public String doPublish(@Param("title") String title,
                            @Param("description") String description,
                            @Param("tag") String tag,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("tag",tag);
        model.addAttribute("title",title);
        model.addAttribute("description",description);


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


        User user = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie target:cookies)
        {
            if(target.getName().equals("token"))
            {
                String token=target.getValue();
                user=userMapper.findByToken(token);
                if(user!=null)
                {
                    request.getSession().setAttribute("githubUser",user);
                }
                break;
            }
        }
        if(user==null)
        {
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getName());

        model.addAttribute("tag",tag);
        model.addAttribute("title",title);
        model.addAttribute("description",description);

        questionMapper.create(question);
        return "redirect:/";

    }
}
