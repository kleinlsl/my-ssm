package com.tujia.myssm.http.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tujia.framework.api.APIResponse;

/**
 * Controller Advice 
 * @author leyuan.lv
 * @create 2022-08-16 20:02
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    public static final String ORIGINAL_REQUEST_EXCEPTION = "original_request_exception";

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public APIResponse exceptionHandler(HttpServletRequest req, HttpServletResponse resp, Throwable e) {
        req.setAttribute(ORIGINAL_REQUEST_EXCEPTION, e);
        return APIResponse.returnFail(-1, e.getMessage());
    }

}