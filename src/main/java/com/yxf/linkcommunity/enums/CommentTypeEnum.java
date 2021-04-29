package com.yxf.linkcommunity.enums;

public enum CommentTypeEnum {
    QUESTION(0),
    COMMENT(1);

    private Integer type;

    public Integer getType(){
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExsit(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.getType()==type)
                return true;
        }
        return false;
    }
}
