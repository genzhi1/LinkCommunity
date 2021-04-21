package com.yxf.linkcommunity.mapper;

import com.yxf.linkcommunity.model.Question;
import com.yxf.linkcommunity.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper{
    @Select("select * from question where creator=#{creator} limit #{offset},#{size}")
    List<Question> listById( @Param("creator") Integer creator, @Param("offset") Integer offset, @Param("size") Integer size) ;

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer getCount();

    @Select("select count(1) from question where creator=#{creator}")
    Integer getCountByCreator(@Param("creator") Integer creator);


}