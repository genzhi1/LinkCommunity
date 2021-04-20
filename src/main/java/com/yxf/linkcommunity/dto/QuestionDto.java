package com.yxf.linkcommunity.dto;

import com.yxf.linkcommunity.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;

}
