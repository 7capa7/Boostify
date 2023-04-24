package com.capa.boostify.controller;

import com.capa.boostify.service.UserBoostingOrderService;
import com.capa.boostify.utils.dto.BoostingOrderDto;
import com.capa.boostify.utils.CreateBoostingOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserBoostingOrderController {
    private final UserBoostingOrderService userBoostingOrderService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public BoostingOrderDto createBoostingOrder(@RequestBody CreateBoostingOrderRequest request){
        return userBoostingOrderService.createBoostingOrder(request);
    }
}
