package com.capa.boostify.boostOrder.service;

import com.capa.boostify.boostOrder.entity.BoostingOrder;
import com.capa.boostify.boostOrder.exception.InvalidIdException;
import com.capa.boostify.boostOrder.repository.BoostingOrderRepository;
import com.capa.boostify.boostOrder.utils.BoostingOrderDto;
import com.capa.boostify.user.entity.User;
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

    private List<BoostingOrderDto> mapBoostingOrderToBoostingOrderDto(List<BoostingOrder> boostingOrders) {
        return boostingOrders.stream().map(boostingOrder -> new BoostingOrderDto(boostingOrder.getId(), boostingOrder.getAccountNickname(),
                boostingOrder.getAccountPassword(), boostingOrder.getActualDivision(), boostingOrder.getExpectedDivision())).collect(Collectors.toList());
    }

    public String assignBoostingOrder(String boostingOrderId) {
        if (boostingOrderId == null) throw new InvalidIdException();

        BoostingOrder boostingOrder = boostingOrderRepository.findById(boostingOrderId)
                .orElseThrow(InvalidIdException::new);

        if (boostingOrder.isFinished() || boostingOrder.getBooster() != null) throw new InvalidIdException();

        boostingOrder.setBooster((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        boostingOrderRepository.save(boostingOrder);

        return "Boosting order " + boostingOrderId + " is now assigned to you!";
    }

    public String completeBoostingOrder(String boostingOrderId) {
        if (boostingOrderId == null) throw new InvalidIdException();

        BoostingOrder boostingOrder = boostingOrderRepository.findById(boostingOrderId)
                .orElseThrow(InvalidIdException::new);

        if (boostingOrder.isFinished() || boostingOrder.getBooster() == null) throw new InvalidIdException();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!boostingOrder.getBooster().equals(user)) throw new InvalidIdException();

        boostingOrder.setFinished(true);
        boostingOrderRepository.save(boostingOrder);
        return "Boosting order " + boostingOrderId + " has been set as completed";

    }
}
