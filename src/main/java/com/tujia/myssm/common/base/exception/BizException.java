package com.tujia.myssm.common.base.exception;


/**
 * @author bowen.ma created on 16/9/21.
 * @version $Id$
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 3450408732802042098L;
    private int code;

    public BizException() {

    }

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }

}
