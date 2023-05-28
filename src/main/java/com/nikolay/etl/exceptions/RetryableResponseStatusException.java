package com.nikolay.etl.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class RetryableResponseStatusException extends ResponseStatusException {
    public RetryableResponseStatusException(HttpStatusCode code, String reason) {
        super(code, reason);
    }
}
