package com.capa.boostify.boostOrder.controller;

import com.capa.boostify.boostOrder.service.BoosterBoostingOrderService;
import com.capa.boostify.boostOrder.utils.BoostingOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booster")
public class BoosterBoostingOrderController {
    private final BoosterBoostingOrderService boosterBoostingOrderService;
    @GetMapping("boosting-orders")
    public List<BoostingOrderDto> getBoostingOrders(){
        return boosterBoostingOrderService.getBoostingOrders();
    }
}
