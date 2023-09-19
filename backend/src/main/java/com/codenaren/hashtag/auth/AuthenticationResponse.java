package com.codenaren.hashtag.auth;

import com.codenaren.hashtag.Dto.CustomerDTO;

public record AuthenticationResponse(CustomerDTO
                                             customerDTO,
                                     String token) {

}
