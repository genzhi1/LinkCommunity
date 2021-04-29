package com.yxf.linkcommunity.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private Integer parentId;
    private Integer type;
    private String content;
}
