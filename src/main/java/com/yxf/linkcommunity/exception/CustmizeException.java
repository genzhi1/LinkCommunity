package com.yxf.linkcommunity.exception;

public class CustmizeException extends  RuntimeException{

    String message;
    Integer code;

    public CustmizeException(MeCustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode(){return code;}
}
