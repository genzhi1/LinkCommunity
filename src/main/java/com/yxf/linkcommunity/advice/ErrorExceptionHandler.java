package com.yxf.linkcommunity.advice;

import com.alibaba.fastjson.JSON;
import com.yxf.linkcommunity.dto.ResultDto;
import com.yxf.linkcommunity.exception.CustmizeException;
import com.yxf.linkcommunity.exception.CustomizeErrorCode;
import okhttp3.Request;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        Throwable ex,
                        Model model,
                        HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDto resultDto = new ResultDto();
            if(ex instanceof CustmizeException){
                resultDto=ResultDto.errorOf((CustmizeException) ex);
            }else{

                resultDto=ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            response.setContentType("application/json");
            response.setStatus(200);
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
            } catch (IOException ioe) {
            }
            writer.write(JSON.toJSONString(resultDto));
            writer.close();

            return null;
        }
        else{
            if(ex instanceof CustmizeException)
            {
                model.addAttribute("message", ex.getMessage());
            }
            else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
        }



    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
