package com.glody.glody_platform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
public class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }
}
