package com.yxf.linkcommunity.service;

import com.yxf.linkcommunity.dto.PagenationDto;
import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.mapper.QuestionMapper;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.Question;
import com.yxf.linkcommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public  PagenationDto findQuestionByUser(Integer id, Integer page, Integer size) {
        Integer offset=(page-1)*size;
        List<Question> questionList=questionMapper.listById(id,offset,size);
        List<QuestionDto> questionDtoList=new LinkedList<>();

        PagenationDto pagenationDto = new PagenationDto();
        for(Question question:questionList)
        {
            User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        pagenationDto.setQuestionDtoList(questionDtoList);
        Integer totalCount=questionMapper.getCount();
        pagenationDto.pageNation(totalCount,page,size);
        return pagenationDto;

    }


    public PagenationDto list(Integer page,Integer size) {
        Integer offset=(page-1)*size;
        List<Question> questionList=questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList=new LinkedList<>();

        PagenationDto pagenationDto = new PagenationDto();
        for(Question question:questionList)
        {
            User user=userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        pagenationDto.setQuestionDtoList(questionDtoList);
        Integer totalCount=questionMapper.getCount();
        pagenationDto.pageNation(totalCount,page,size);
        return pagenationDto;
    }


}
