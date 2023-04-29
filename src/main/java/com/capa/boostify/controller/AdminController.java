package com.capa.boostify.controller;

import com.capa.boostify.service.AdminService;
import com.capa.boostify.utils.ApplicationStatus;
import com.capa.boostify.utils.BoosterApplicationDecision;
import com.capa.boostify.utils.BoosterApplicationStatusDeserializer;
import com.capa.boostify.utils.dto.BoosterApplicationDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    public String decideBoosterApplication(@RequestParam(name = "boosterApplicationId", defaultValue = "") String id,
                                           @RequestParam(name = "applicationStatus") @JsonDeserialize(using = BoosterApplicationStatusDeserializer.class) ApplicationStatus applicationStatus) {
        return adminService.decideBoosterApplication(id,applicationStatus);
    }

    @GetMapping("/applications")
    @ResponseStatus(HttpStatus.OK)
    public List<BoosterApplicationDto> getBoosterApplications(@RequestParam(name = "pendingOnly", defaultValue = "false") boolean pendingOnly) {
        return adminService.getBoosterApplications(pendingOnly);
    }
}
