package com.capa.boostify.controller;

import com.capa.boostify.service.BoosterBoostingOrderService;
import com.capa.boostify.utils.dto.BoostingOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booster")
public class BoosterBoostingOrderController {
    private final BoosterBoostingOrderService boosterBoostingOrderService;
    @GetMapping("orders")
    @ResponseStatus(HttpStatus.OK)
    public List<BoostingOrderDto> getBoostingOrders(){
        return boosterBoostingOrderService.getBoostingOrders();
    }
    @GetMapping("assign")
    @ResponseStatus(HttpStatus.OK)
    public String assignBoostingOrder(@RequestParam(name = "id") String orderId){
        return boosterBoostingOrderService.assignBoostingOrder(orderId);
    }
        @GetMapping("complete")
    @ResponseStatus(HttpStatus.OK)
    public String completeBoostingOrder(@RequestParam(name = "id") String orderId){
        return boosterBoostingOrderService.completeBoostingOrder(orderId);
    }
}
