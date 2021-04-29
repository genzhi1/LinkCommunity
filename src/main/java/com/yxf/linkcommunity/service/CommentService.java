package com.yxf.linkcommunity.service;

import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.enums.CommentTypeEnum;
import com.yxf.linkcommunity.exception.CustmizeException;
import com.yxf.linkcommunity.exception.CustomizeErrorCode;
import com.yxf.linkcommunity.mapper.CommentMapper;
import com.yxf.linkcommunity.mapper.QuestionExtMapper;
import com.yxf.linkcommunity.mapper.QuestionMapper;
import com.yxf.linkcommunity.model.Comment;
import com.yxf.linkcommunity.model.CommentExample;
import com.yxf.linkcommunity.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

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

    public List<QuestionDto> getCommentByQuestionId(Integer id) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(example);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        List<Integer> commentors = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toList());
        

    }
}
