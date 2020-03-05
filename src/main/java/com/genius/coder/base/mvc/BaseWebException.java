package com.genius.coder.base.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BaseWebException extends ResponseStatusException {
    public BaseWebException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public BaseWebException(HttpStatus status, String message) {
        super(status, message);
    }

    public BaseWebException(String message, Throwable throwable) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message, throwable);
    }

    public BaseWebException(HttpStatus status, String message, Throwable throwable) {
        super(status, message, throwable);
    }
}