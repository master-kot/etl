package com.nikolay.etl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class HttpClientException extends RuntimeException {

    public HttpClientException(String message) {
        super(message);
    }
}
