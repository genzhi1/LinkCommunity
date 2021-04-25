package com.yxf.linkcommunity.exception;

public class CustmizeException extends  RuntimeException{

    String message;


    public CustmizeException(String message){
        this.message=message;
    }

    public CustmizeException(MeCustomizeErrorCode errorCode){
        this.message=errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
