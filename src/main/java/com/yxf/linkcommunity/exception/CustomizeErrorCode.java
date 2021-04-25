package com.yxf.linkcommunity.exception;

public enum CustomizeErrorCode implements MeCustomizeErrorCode{
    QUESTION_NOT_FOUND("问题找不到，换个试试吧");

    private String message;

    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message)
    {
        this.message=message;
    }
}
