package com.capa.boostify.boostOrder.service;

import com.capa.boostify.boostOrder.entity.BoostingOrder;
import com.capa.boostify.boostOrder.repository.BoostingOrderRepository;
import com.capa.boostify.boostOrder.utils.BoostingOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoosterBoostingOrderService {
    private final BoostingOrderRepository boostingOrderRepository;

    public List<BoostingOrderDto> getBoostingOrders() {
        return mapBoostingOrderToBoostingOrderDto(boostingOrderRepository.findBoostingOrdersByBoosterIsNull());
    }

    private List<BoostingOrderDto> mapBoostingOrderToBoostingOrderDto(List<BoostingOrder> boostingOrders) {
        return boostingOrders.stream().map(boostingOrder -> new BoostingOrderDto(boostingOrder.getId(), boostingOrder.getAccountNickname(),
                boostingOrder.getAccountPassword(), boostingOrder.getActualDivision(),boostingOrder.getExpectedDivision())).collect(Collectors.toList());
    }
}
