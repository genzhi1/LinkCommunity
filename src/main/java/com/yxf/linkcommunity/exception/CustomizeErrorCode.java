package com.yxf.linkcommunity.exception;

public enum CustomizeErrorCode implements MeCustomizeErrorCode{
    QUESTION_NOT_FOUND(1020,"问题找不到，换个试试吧"),
    USER_NOT_LOGIN(1021,"用户未登录"),
    PARENT_QUSTION_NOT_BIND(1022,"未选择回复问题或评论"),
    OK_SUCCESS(1024,"成功操作"),
    TYPE_NOT_MATCH(1025,"评论对象类型有误"),
    PARENT_COMMENT_NOT_EXSIT(1026,"该评论不存在"),
    PARENT_QUESTION_NOT_EXSIT(1027,"该问题不存在"),
    SYSTEM_ERROR(1028,"服务器错误"),
    CONTENT_IS_NULL(1029,"评论不能为空");

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
