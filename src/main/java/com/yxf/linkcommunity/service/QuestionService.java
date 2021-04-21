package com.yxf.linkcommunity.service;

import com.yxf.linkcommunity.mapper.QuestionMapper;
import com.yxf.linkcommunity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

//    public  PagenationDto findQuestionByUser(Integer creator, Integer page, Integer size) {
//        Integer offset=(page-1)*size;
//        List<Question> questionList=questionMapper.listById(creator,offset,size);
//        List<QuestionDto> questionDtoList=new LinkedList<>();
//
//        PagenationDto pagenationDto = new PagenationDto();
//        for(Question question:questionList)
//        {
//            UserExample userExample = new UserExample();
//            userExample.createCriteria().andIdEqualTo(question.getCreator());
//            List<User> users = userMapper.selectByExample(userExample);
//            User user= users.get(0);
//            QuestionDto questionDto = new QuestionDto();
//            BeanUtils.copyProperties(question,questionDto);
//            questionDto.setUser(user);
//            questionDtoList.add(questionDto);
//        }
//
//        pagenationDto.setQuestionDtoList(questionDtoList);
//        Integer totalCount=questionMapper.getCountByCreator(creator);
//        pagenationDto.setTotalCount(totalCount);
//        pagenationDto.pageNation(totalCount,page,size);
//        return pagenationDto;
//
//    }

//
//    public PagenationDto list(Integer page,Integer size) {
//        Integer offset=(page-1)*size;
//        List<Question> questionList=questionMapper.list(offset,size);
//        List<QuestionDto> questionDtoList=new LinkedList<>();
//
//        PagenationDto pagenationDto = new PagenationDto();
//        for(Question question:questionList)
//        {
//            UserExample userExample = new UserExample();
//            userExample.createCriteria().andIdEqualTo(question.getCreator());
//            List<User> users = userMapper.selectByExample(userExample);
//            User user=  users.get(0);
//            QuestionDto questionDto = new QuestionDto();
//            BeanUtils.copyProperties(question,questionDto);
//            questionDto.setUser(user);
//            questionDtoList.add(questionDto);
//        }
//
//        pagenationDto.setQuestionDtoList(questionDtoList);
//        Integer totalCount=questionMapper.getCount();
//        pagenationDto.setTotalCount(totalCount);
//        pagenationDto.pageNation(totalCount,page,size);
//        return pagenationDto;
//    }
//

}
