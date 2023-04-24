package com.capa.boostify.controller;

import com.capa.boostify.service.AdminService;
import com.capa.boostify.utils.BoosterApplicationDecision;
import com.capa.boostify.utils.dto.BoosterApplicationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/decide")
    @ResponseStatus(HttpStatus.OK)
    public String decideBoosterApplication(@RequestBody BoosterApplicationDecision decision) {
        return adminService.decideBoosterApplication(decision);
    }

    @GetMapping("/applications")
    @ResponseStatus(HttpStatus.OK)
    public List<BoosterApplicationDto> getBoosterApplications(@RequestParam(name = "pendingOnly", defaultValue = "false") boolean pendingOnly) {
        return adminService.getBoosterApplications(pendingOnly);
    }
}
