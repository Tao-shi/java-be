package com.example.javabe.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String field, String value) {
        super("User already exists with " + field + " '" + value + "'");
    }
}
