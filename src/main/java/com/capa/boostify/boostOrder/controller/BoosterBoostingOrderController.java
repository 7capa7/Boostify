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
    @PostMapping("assign-boosting-order")
    @ResponseStatus(HttpStatus.OK)
    public String assignBoostingOrder(@RequestParam(name = "id") String boostingOrderId){
        return boosterBoostingOrderService.assignBoostingOrder(boostingOrderId);
    }
    @PostMapping("complete-boosting-order")
    @ResponseStatus(HttpStatus.OK)
    public String completeBoostingOrder(@RequestParam(name = "id") String boostingOrderId){
        return boosterBoostingOrderService.completeBoostingOrder(boostingOrderId);
    }
}
