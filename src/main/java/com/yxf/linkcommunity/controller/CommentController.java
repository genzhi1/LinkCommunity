package com.yxf.linkcommunity.controller;

import com.yxf.linkcommunity.dto.CommentCreateDto;
import com.yxf.linkcommunity.dto.ResultDto;
import com.yxf.linkcommunity.exception.CustomizeErrorCode;
import com.yxf.linkcommunity.model.Comment;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired(required = false)
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request){
        if(request.getSession()==null)
            return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        User githubUser = (User) request.getSession().getAttribute("githubUser");
        if(githubUser==null||githubUser.getId()==0)
           return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        if(commentCreateDto==null|| StringUtils.isBlank(commentCreateDto.getContent())){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_NULL);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentor(24);
        commentService.insert(comment);
        return ResultDto.ok();


    }

}
