package com.codenaren.hashtag.Exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
@Slf4j
public class ResourceNotEntered extends RuntimeException {

    public ResourceNotEntered(String message) {
        super(message);
        log.info("ResourceNotEntered Method called");
    }
}
