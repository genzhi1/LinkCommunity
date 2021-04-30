package com.yxf.linkcommunity.dto;

import com.yxf.linkcommunity.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
}
