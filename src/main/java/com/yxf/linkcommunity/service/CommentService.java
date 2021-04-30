package com.yxf.linkcommunity.service;

import com.yxf.linkcommunity.dto.CommentDto;
import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.enums.CommentTypeEnum;
import com.yxf.linkcommunity.exception.CustmizeException;
import com.yxf.linkcommunity.exception.CustomizeErrorCode;
import com.yxf.linkcommunity.mapper.CommentMapper;
import com.yxf.linkcommunity.mapper.QuestionExtMapper;
import com.yxf.linkcommunity.mapper.QuestionMapper;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.*;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0)
            throw new CustmizeException(CustomizeErrorCode.PARENT_QUSTION_NOT_BIND);
        if (comment.getType() == null || !CommentTypeEnum.isExsit(comment.getType()))
            throw new CustmizeException(CustomizeErrorCode.TYPE_NOT_MATCH);
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {    //回复评论
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (parentComment == null) {
                throw new CustmizeException(CustomizeErrorCode.PARENT_COMMENT_NOT_EXSIT);
            } else {
                commentMapper.insertSelective(comment);
            }
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustmizeException(CustomizeErrorCode.PARENT_QUESTION_NOT_EXSIT);
            } else {
                commentMapper.insertSelective(comment);
                question.setLikeCount(1);
                questionExtMapper.incCommentCount(question);
            }

        }
    }

    public List<CommentDto> getCommentByQuestionId(Integer id) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        Set<Integer> commentors = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<Integer> commentorList= new ArrayList<>(commentors);


        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(commentorList);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentor()));
            return commentDto;
        }).collect(Collectors.toList());


        return  commentDtos;

    }
}
