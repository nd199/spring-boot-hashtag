package com.codenaren.hashtag;

import jakarta.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class SayHelloToUser {
    private static LocalDateTime dateTime;

    record SayHello(String message){}

    @GetMapping("/Hi")
    public SayHello sayHello(){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy:mm:dd & HH:MM:SS");
        String format = dateTime.format(timeFormatter);
        return new SayHello(" Hello Developer Time now is %s".formatted(format));
    }
}
