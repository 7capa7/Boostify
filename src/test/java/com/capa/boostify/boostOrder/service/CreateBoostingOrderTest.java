package com.capa.boostify.boostOrder.service;

import com.capa.boostify.entity.BoostingOrder;
import com.capa.boostify.exception.BoostingOrderAlreadyExistsException;
import com.capa.boostify.exception.InvalidBoostingOrderDataException;
import com.capa.boostify.exception.InvalidDivisionsException;
import com.capa.boostify.repository.BoostingOrderRepository;
import com.capa.boostify.service.UserBoostingOrderService;
import com.capa.boostify.utils.dto.BoostingOrderDto;
import com.capa.boostify.utils.CreateBoostingOrderRequest;
import com.capa.boostify.entity.User;
import com.capa.boostify.utils.Division;
import com.capa.boostify.utils.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBoostingOrderTest {
    @InjectMocks
    private UserBoostingOrderService userBoostingOrderService;
    @Mock
    private BoostingOrderRepository boostingOrderRepository;

    @Test
    public void testCreateBoostingOrderSuccess() {
        CreateBoostingOrderRequest createBoostingOrderRequest = new CreateBoostingOrderRequest();
        createBoostingOrderRequest.setAccountNickname("test_nickname");
        createBoostingOrderRequest.setAccountPassword("test_password");
        createBoostingOrderRequest.setActualDivision(Division.PLATINUM);
        createBoostingOrderRequest.setExpectedDivision(Division.CHALLENGER);

        User user = new User();
        user.setId(UUID.randomUUID().toString());

        Authentication auth = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(boostingOrderRepository.save(any(BoostingOrder.class))).thenAnswer((Answer<BoostingOrder>) invocation -> {
            BoostingOrder order = invocation.getArgument(0);
            order.setId(UUID.randomUUID().toString());
            return order;
        });

        when(boostingOrderRepository.findBoostingOrdersByUser(user)).thenReturn(List.of());

        BoostingOrderDto boostingOrderDto = userBoostingOrderService.createBoostingOrder(createBoostingOrderRequest);

        assertEquals(boostingOrderDto.accountNickname(), createBoostingOrderRequest.getAccountNickname());
        assertEquals(boostingOrderDto.accountPassword(), createBoostingOrderRequest.getAccountPassword());
        assertEquals(boostingOrderDto.actualDivision(), createBoostingOrderRequest.getActualDivision());
        assertEquals(boostingOrderDto.expectedDivision(), createBoostingOrderRequest.getExpectedDivision());
    }

    @Test
    void createBoostingOrder_withInvalidRequest_throwsException() {
        CreateBoostingOrderRequest invalidRequest = new CreateBoostingOrderRequest(null, null, Division.PLATINUM, Division.DIAMOND);

        Assertions.assertThrows(InvalidBoostingOrderDataException.class, () -> {
            userBoostingOrderService.createBoostingOrder(invalidRequest);
        });
    }

    @Test
    void createBoostingOrder_withExistingUncompletedOrder_throwsException() {
        User user = new User("id", "email", "password", Role.USER, null);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);

        BoostingOrder existingOrder = BoostingOrder.builder()
                .user(user)
                .accountNickname("existingNickname")
                .accountPassword("existingPassword")
                .actualDivision(Division.PLATINUM)
                .expectedDivision(Division.DIAMOND)
                .booster(null)
                .isFinished(false)
                .build();

        when(boostingOrderRepository.findBoostingOrdersByUser(any())).thenReturn(Collections.singletonList(existingOrder));

        CreateBoostingOrderRequest validRequest = new CreateBoostingOrderRequest("account", "password", Division.PLATINUM, Division.DIAMOND);


        Assertions.assertThrows(BoostingOrderAlreadyExistsException.class, () -> {
            userBoostingOrderService.createBoostingOrder(validRequest);
        });
    }

    @Test
    public void testCreateBoostingOrder_InvalidDivision() {
        CreateBoostingOrderRequest createBoostingOrderRequest = new CreateBoostingOrderRequest();
        createBoostingOrderRequest.setAccountNickname("test_nickname");
        createBoostingOrderRequest.setAccountPassword("test_password");
        createBoostingOrderRequest.setActualDivision(Division.PLATINUM);
        createBoostingOrderRequest.setExpectedDivision(Division.IRON);

        User user = new User();
        user.setId(UUID.randomUUID().toString());

        Authentication auth = new TestingAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(boostingOrderRepository.findBoostingOrdersByUser(user)).thenReturn(List.of());

        assertThrows(InvalidDivisionsException.class, () -> {
            userBoostingOrderService.createBoostingOrder(createBoostingOrderRequest);
        });
    }
}