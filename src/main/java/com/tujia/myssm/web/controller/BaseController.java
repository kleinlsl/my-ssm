package com.tujia.myssm.web.controller;

import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tujia.framework.api.APIResponse;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/09/28 15:13
 */
@Slf4j
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public APIResponse<Void> handleException(Exception e) {
        log.warn("[BaseController.handleException] Error,msg={}", e.getMessage(), e);
        if (e instanceof UnexpectedRollbackException) {
            String errMsg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            return APIResponse.returnFail(errMsg);
        }
        return APIResponse.returnFail(e.getMessage());
    }
}
