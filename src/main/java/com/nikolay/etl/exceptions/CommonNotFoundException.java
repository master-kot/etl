package com.nikolay.etl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommonNotFoundException extends ResponseStatusException {
    public CommonNotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
