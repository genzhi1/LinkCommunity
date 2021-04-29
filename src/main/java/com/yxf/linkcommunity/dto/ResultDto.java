package com.yxf.linkcommunity.dto;

import com.yxf.linkcommunity.exception.CustmizeException;
import com.yxf.linkcommunity.exception.CustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDto {
    private Integer code;
    private String message;

    public static  ResultDto errorOf(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }

    public static  ResultDto errorOf(CustomizeErrorCode customizeErrorCode)
    {
        return errorOf(customizeErrorCode.getCode(), customizeErrorCode.getMessage());
    }

    public static ResultDto errorOf(CustmizeException ex){
        return errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDto ok(){
        return errorOf(CustomizeErrorCode.OK_SUCCESS);
    }

}
