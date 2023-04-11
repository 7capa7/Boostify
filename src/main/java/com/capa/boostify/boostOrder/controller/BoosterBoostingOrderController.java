package com.capa.boostify.boostOrder.controller;

import com.capa.boostify.boostOrder.service.BoosterBoostingOrderService;
import com.capa.boostify.boostOrder.utils.BoostingOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booster")
public class BoosterBoostingOrderController {
    private final BoosterBoostingOrderService boosterBoostingOrderService;
    @GetMapping("boosting-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<BoostingOrderDto> getBoostingOrders(){
        return boosterBoostingOrderService.getBoostingOrders();
    }
    @PutMapping("assign-boosting-order")
    public String assignBoostingOrder(@RequestParam(name = "id") String boostingOrderId){
        return boosterBoostingOrderService.assignBoostingOrder(boostingOrderId);
    }
}
