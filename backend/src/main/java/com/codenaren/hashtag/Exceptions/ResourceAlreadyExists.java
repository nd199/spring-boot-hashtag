package com.codenaren.hashtag.Exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Slf4j
public class ResourceAlreadyExists extends RuntimeException {


    public ResourceAlreadyExists(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s found with %s : '%s'", resourceName, fieldName, fieldValue));
        log.info("Resource Already exist exception called on : {},{},{}",
                resourceName, fieldName, fieldValue);
    }
}
