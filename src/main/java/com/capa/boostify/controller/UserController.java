package com.capa.boostify.controller;

import com.capa.boostify.service.UserService;
import com.capa.boostify.utils.BoosterApplicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/booster-application")
    @ResponseStatus(HttpStatus.OK)
    public String applyForBooster(@RequestBody BoosterApplicationRequest boosterApplicationRequest){
        return userService.applyForBooster(boosterApplicationRequest);
    }
}
