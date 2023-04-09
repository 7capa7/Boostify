package com.capa.boostify.user.service.admin;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterApplication;
import com.capa.boostify.user.exception.InvalidBoosterApplicationDecideDataException;
import com.capa.boostify.user.repository.BoosterApplicationRepository;
import com.capa.boostify.user.repository.UserRepository;
import com.capa.boostify.user.utils.BoosterApplicationDecide;
import com.capa.boostify.user.utils.BoosterApplicationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DecideBoosterApplicationTest {

    @Mock
    private BoosterApplicationRepository boosterApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testDecideBoosterApplicationWithDeclinedStatus() {
        BoosterApplicationDecide boosterApplicationDecide = new BoosterApplicationDecide();
        boosterApplicationDecide.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecide.setBoosterApplicationStatus(BoosterApplicationStatus.DECLINED);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setBoosterApplicationStatus(BoosterApplicationStatus.PENDING);

        when(boosterApplicationRepository.findById(boosterApplicationDecide.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));

        String result = adminService.decideBoosterApplication(boosterApplicationDecide);

        verify(boosterApplicationRepository, times(1)).findById(boosterApplicationDecide.getBoosterApplicationId());
        verify(boosterApplicationRepository, times(1)).save(boosterApplication);

        assertEquals("Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status updated for Declined !", result);
        assertEquals(BoosterApplicationStatus.DECLINED, boosterApplication.getBoosterApplicationStatus());
    }

    @Test
    public void testDecideBoosterApplicationWithAcceptedStatus() {
        BoosterApplicationDecide boosterApplicationDecide = new BoosterApplicationDecide();
        boosterApplicationDecide.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecide.setBoosterApplicationStatus(BoosterApplicationStatus.ACCEPTED);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setBoosterApplicationStatus(BoosterApplicationStatus.PENDING);

        when(boosterApplicationRepository.findById(boosterApplicationDecide.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        boosterApplication.setUser(User.builder().id(UUID.randomUUID().toString()).build());
        String result = adminService.decideBoosterApplication(boosterApplicationDecide);

        verify(boosterApplicationRepository, times(1)).findById(boosterApplicationDecide.getBoosterApplicationId());
        verify(boosterApplicationRepository, times(1)).save(boosterApplication);

        assertEquals("Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status updated for Accepted, this user is now booster !", result);
        assertEquals(BoosterApplicationStatus.ACCEPTED, boosterApplication.getBoosterApplicationStatus());
    }

    @Test
    public void testDecideBoosterApplicationWithInvalidData() {
        BoosterApplicationDecide boosterApplicationDecide = new BoosterApplicationDecide();

        InvalidBoosterApplicationDecideDataException exception = assertThrows(InvalidBoosterApplicationDecideDataException.class, () -> adminService.decideBoosterApplication(boosterApplicationDecide));

        verify(boosterApplicationRepository, never()).findById(anyString());
        verify(boosterApplicationRepository, never()).save(any(BoosterApplication.class));

        assertEquals("Invalid Data! Cannot decide about booster", exception.getMessage());
    }

    @Test
    void testDecideBoosterApplicationWithPendingStatus() {
        BoosterApplicationDecide boosterApplicationDecide = new BoosterApplicationDecide();
        boosterApplicationDecide.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecide.setBoosterApplicationStatus(BoosterApplicationStatus.PENDING);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setBoosterApplicationStatus(BoosterApplicationStatus.PENDING);

        when(boosterApplicationRepository.findById(boosterApplicationDecide.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));

        String result = adminService.decideBoosterApplication(boosterApplicationDecide);

        verify(boosterApplicationRepository, times(1)).findById(boosterApplicationDecide.getBoosterApplicationId());
        verify(boosterApplicationRepository, times(1)).save(boosterApplication);

        assertEquals("Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status not changed !", result);
        assertEquals(BoosterApplicationStatus.PENDING, boosterApplication.getBoosterApplicationStatus());
    }

    @Test
    void decideBoosterApplication_whenBoosterApplicationIsNotPending_thenShouldReturnAlreadyResolved() {
        BoosterApplicationDecide boosterApplicationDecide = new BoosterApplicationDecide();
        boosterApplicationDecide.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecide.setBoosterApplicationStatus(BoosterApplicationStatus.DECLINED);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setBoosterApplicationStatus(BoosterApplicationStatus.ACCEPTED);

        when(boosterApplicationRepository.findById(boosterApplicationDecide.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));

        String result = adminService.decideBoosterApplication(boosterApplicationDecide);

        assertEquals(result,"This application is already resolved !");

    }

}