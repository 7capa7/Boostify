package com.capa.boostify.service;

import com.capa.boostify.entity.BoostingOrder;
import com.capa.boostify.exception.InvalidIdException;
import com.capa.boostify.repository.BoostingOrderRepository;
import com.capa.boostify.utils.dto.BoostingOrderDto;
import com.capa.boostify.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private List<BoostingOrderDto> mapBoostingOrderToBoostingOrderDto(List<BoostingOrder> orders) {
        return orders.stream().map(order -> new BoostingOrderDto(order.getId(), order.getNickname(),
                order.getPassword(), order.getActualDivision(), order.getExpectedDivision())).collect(Collectors.toList());
    }

    public String assignBoostingOrder(String orderId) {
        if (orderId == null) throw new InvalidIdException();

        BoostingOrder order = boostingOrderRepository.findById(orderId)
                .orElseThrow(InvalidIdException::new);

        if (order.isFinished() || order.getBooster() != null) throw new InvalidIdException();

        order.setBooster((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        boostingOrderRepository.save(order);

        return "Boosting order " + orderId + " is now assigned to you!";
    }

    public String completeBoostingOrder(String orderId) {
        if (orderId == null) throw new InvalidIdException();

        BoostingOrder order = boostingOrderRepository.findById(orderId)
                .orElseThrow(InvalidIdException::new);

        if (order.isFinished() || order.getBooster() == null) throw new InvalidIdException();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!order.getBooster().equals(user)) throw new InvalidIdException();

        order.setFinished(true);
        boostingOrderRepository.save(order);
        return "Boosting order " + orderId + " has been set as completed";

    }
}
