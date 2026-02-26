package com.blog.common.exception;

import com.blog.common.constant.ResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException(String message) {
        super(message);
        this.code = 400;
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
