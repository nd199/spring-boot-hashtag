package com.codenaren.hashtag.Exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Slf4j
public class ResourceAlreadyExists extends RuntimeException {


    public ResourceAlreadyExists(String message) {
        super(message);
        log.info("Resource already found Invoked");
    }
}
