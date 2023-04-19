package com.capa.boostify.boostOrder.controller;

import com.capa.boostify.boostOrder.service.UserBoostingOrderService;
import com.capa.boostify.boostOrder.utils.BoostingOrderDto;
import com.capa.boostify.boostOrder.utils.CreateBoostingOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserBoostingOrderController {
    private final UserBoostingOrderService userBoostingOrderService;

    @PostMapping("/boosting-order")
    @ResponseStatus(HttpStatus.CREATED)
    public BoostingOrderDto createBoostingOrder(@RequestBody CreateBoostingOrderRequest createBoostingOrderRequest){
        return userBoostingOrderService.createBoostingOrder(createBoostingOrderRequest);
    }
}
