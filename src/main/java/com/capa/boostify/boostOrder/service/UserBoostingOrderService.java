package com.capa.boostify.boostOrder.service;

import com.capa.boostify.boostOrder.entity.BoostingOrder;
import com.capa.boostify.boostOrder.exception.BoostingOrderAlreadyExistsException;
import com.capa.boostify.boostOrder.exception.InvalidBoostingOrderDataException;
import com.capa.boostify.boostOrder.exception.InvalidDivisionsException;
import com.capa.boostify.boostOrder.repository.BoostingOrderRepository;
import com.capa.boostify.boostOrder.utils.BoostingOrderDto;
import com.capa.boostify.boostOrder.utils.CreateBoostingOrderRequest;
import com.capa.boostify.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBoostingOrderService {
    private final BoostingOrderRepository boostingOrderRepository;

    public BoostingOrderDto createBoostingOrder(CreateBoostingOrderRequest createBoostingOrderRequest) {
        if (!createBoostingOrderRequest.isValid()) throw new InvalidBoostingOrderDataException();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (boostingOrderAlreadyExist(user)) throw new BoostingOrderAlreadyExistsException();

        if (createBoostingOrderRequest.getActualDivision().ordinal() >= createBoostingOrderRequest.getExpectedDivision().ordinal())
            throw new InvalidDivisionsException();

        BoostingOrder boostingOrder = BoostingOrder.builder()
                .user(user)
                .accountNickname(createBoostingOrderRequest.getAccountNickname())
                .accountPassword(createBoostingOrderRequest.getAccountPassword())
                .actualDivision(createBoostingOrderRequest.getActualDivision())
                .expectedDivision(createBoostingOrderRequest.getExpectedDivision())
                .booster(null)
                .isFinished(false)
                .build();

        BoostingOrder save = boostingOrderRepository.save(boostingOrder);

        return new BoostingOrderDto(save.getId(), save.getAccountNickname(), save.getAccountPassword(), save.getActualDivision(), save.getExpectedDivision());
    }

    private boolean boostingOrderAlreadyExist(User user) {
        return boostingOrderRepository.findBoostingOrdersByUser(user).stream().anyMatch(boostingOrder -> !boostingOrder.isFinished());
    }
}
