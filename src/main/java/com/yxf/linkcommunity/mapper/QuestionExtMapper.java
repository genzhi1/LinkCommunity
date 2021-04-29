package com.yxf.linkcommunity.mapper;

import com.yxf.linkcommunity.model.Comment;
import com.yxf.linkcommunity.model.Question;
import com.yxf.linkcommunity.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int incViewCount(Question record);
    int incCommentCount(Question question);



}