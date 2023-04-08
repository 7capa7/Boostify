package com.capa.boostify.authentication.controller;

import com.capa.boostify.authentication.service.AuthenticationService;
import com.capa.boostify.authentication.utils.LoginRequest;
import com.capa.boostify.authentication.utils.LoginResponse;
import com.capa.boostify.authentication.utils.RegisterRequest;
import com.capa.boostify.user.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@RequestBody LoginRequest loginRequest){
        return authenticationService.login(loginRequest);
    }
}
