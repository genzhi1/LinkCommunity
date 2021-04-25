package com.yxf.linkcommunity.advice;

import com.yxf.linkcommunity.exception.CustmizeException;
import okhttp3.Request;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        Throwable ex,
                        Model model) {
        HttpStatus status = getStatus(request);

        if(ex instanceof CustmizeException)
        {
            model.addAttribute("message", ex.getMessage());
        }
        else {
            model.addAttribute("message", "服务器冒烟了，换个方式吧");
        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
