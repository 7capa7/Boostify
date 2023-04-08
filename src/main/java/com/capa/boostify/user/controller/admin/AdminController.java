package com.capa.boostify.user.controller.admin;

import com.capa.boostify.user.service.admin.AdminService;
import com.capa.boostify.user.utils.BoosterApplicationDecide;
import com.capa.boostify.user.utils.BoosterApplicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/booster-application-decide")
    @ResponseStatus(HttpStatus.OK)
    public String decideBoosterApplication(@RequestBody BoosterApplicationDecide boosterApplicationDecide){
        return adminService.decideBoosterApplication(boosterApplicationDecide);
    }

}
