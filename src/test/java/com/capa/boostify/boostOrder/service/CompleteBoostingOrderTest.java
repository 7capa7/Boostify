package com.capa.boostify.boostOrder.service;

import com.capa.boostify.boostOrder.entity.BoostingOrder;
import com.capa.boostify.boostOrder.exception.InvalidIdException;
import com.capa.boostify.boostOrder.repository.BoostingOrderRepository;
import com.capa.boostify.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompleteBoostingOrderTest {
    @Mock
    private BoostingOrderRepository boostingOrderRepository;

    @InjectMocks
    private BoosterBoostingOrderService boostingOrderService;

    @Test
    public void testCompleteBoostingOrder() {
        String boostingOrderId = "exampleId";
        BoostingOrder boostingOrder = new BoostingOrder();
        boostingOrder.setBooster(User.builder().id("id").email("email").build());
        when(boostingOrderRepository.findById(boostingOrderId)).thenReturn(Optional.of(boostingOrder));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(boostingOrder.getBooster(), null));

        String result = boostingOrderService.completeBoostingOrder(boostingOrderId);

        assertTrue(boostingOrder.isFinished());
        assertEquals("Boosting order " + boostingOrderId + " has been set as completed", result);
    }

    @Test
    public void testCompleteBoostingOrderInvalidUser() {
        String boostingOrderId = "exampleId";
        BoostingOrder boostingOrder = new BoostingOrder();
        boostingOrder.setBooster(User.builder().id("id").email("email").build());
        when(boostingOrderRepository.findById(boostingOrderId)).thenReturn(Optional.of(boostingOrder));
        Authentication authentication = new UsernamePasswordAuthenticationToken(User.builder().id("secondId").email("secondEmail").build(), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(InvalidIdException.class, () -> boostingOrderService.completeBoostingOrder(boostingOrderId));
        assertFalse(boostingOrder.isFinished());
    }
}
