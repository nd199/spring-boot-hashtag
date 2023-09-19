package com.codenaren.hashtag.auth;

import com.codenaren.hashtag.Dto.CustomerDTO;
import com.codenaren.hashtag.Dto.Mapper.CustomerDTOMapper;
import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Security.Jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final CustomerDTOMapper customerDTOMapper;

    private final JWTUtil jwtUtil;

    public AuthenticationResponse login(AuthenticationRequest
                                                authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.userName(),
                        authenticationRequest.password()
                )
        );
        Customer principal = (Customer) authentication.getPrincipal();
        CustomerDTO customerDTO = customerDTOMapper.apply(principal);
        String token = jwtUtil.issueToken(customerDTO.userName(), customerDTO.roles());
        return new AuthenticationResponse(customerDTO, token);
    }
}
