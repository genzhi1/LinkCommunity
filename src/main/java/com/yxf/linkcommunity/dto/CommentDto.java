package com.yxf.linkcommunity.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
}
