package com.capa.boostify.controller;

import com.capa.boostify.service.AuthenticationService;
import com.capa.boostify.utils.LoginRequest;
import com.capa.boostify.utils.LoginResponse;
import com.capa.boostify.utils.RegisterRequest;
import com.capa.boostify.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }
}
