package com.yxf.linkcommunity.service;

import com.yxf.linkcommunity.dto.PagenationDto;
import com.yxf.linkcommunity.dto.QuestionDto;
import com.yxf.linkcommunity.mapper.QuestionMapper;
import com.yxf.linkcommunity.mapper.UserMapper;
import com.yxf.linkcommunity.model.Question;
import com.yxf.linkcommunity.model.QuestionExample;
import com.yxf.linkcommunity.model.User;
import com.yxf.linkcommunity.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    public PagenationDto findQuestionByUser(Integer creator, Integer page, Integer size) {
        Integer offset=(page-1)*size;
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(creator);
        List<Question> questionList=questionMapper.selectByExampleWithRowbounds(questionExample1,new RowBounds(offset,size));
        List<QuestionDto> questionDtoList=new LinkedList<>();

        PagenationDto pagenationDto = new PagenationDto();
        for(Question question:questionList)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            User user= users.get(0);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        pagenationDto.setQuestionDtoList(questionDtoList);
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(creator);
        Integer totalCount=(int)questionMapper.countByExample(questionExample);
        pagenationDto.setTotalCount(totalCount);
        pagenationDto.pageNation(totalCount,page,size);
        return pagenationDto;

    }


    public PagenationDto list(Integer page,Integer size) {
        Integer offset=(page-1)*size;
        List<Question> questionList=questionMapper
                .selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDto> questionDtoList=new LinkedList<>();

        PagenationDto pagenationDto = new PagenationDto();
        for(Question question:questionList)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            User user=  users.get(0);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        pagenationDto.setQuestionDtoList(questionDtoList);
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
        pagenationDto.setTotalCount(totalCount);
        pagenationDto.pageNation(totalCount,page,size);
        return pagenationDto;
    }


    public QuestionDto getUserByQuestion(Integer id) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(id);
        List<Question> questionList=questionMapper.selectByExample(questionExample);
        Question question=questionList.get(0);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }
}
