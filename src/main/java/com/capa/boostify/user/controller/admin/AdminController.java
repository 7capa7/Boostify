package com.capa.boostify.user.controller.admin;

import com.capa.boostify.user.service.admin.AdminService;
import com.capa.boostify.user.utils.BoosterApplicationDecide;
import com.capa.boostify.user.utils.BoosterApplicationRequest;
import com.capa.boostify.user.utils.dto.BoosterApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/booster-application-decide")
    @ResponseStatus(HttpStatus.OK)
    public String decideBoosterApplication(@RequestBody BoosterApplicationDecide boosterApplicationDecide) {
        return adminService.decideBoosterApplication(boosterApplicationDecide);
    }

    @GetMapping("/booster-applications")
    @ResponseStatus(HttpStatus.OK)
    public List<BoosterApplicationDto> getBoosterApplications(@RequestParam(name = "pendingOnly", defaultValue = "false") boolean pendingOnly) {
        return adminService.getBoosterApplications(pendingOnly);
    }
}
