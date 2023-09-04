package com.codenaren.hashtag.Utils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    record PingPong(String message){}

    @GetMapping("/ping")
    public PingPong getPong(String message){
        return new PingPong("pong");
    }
}
