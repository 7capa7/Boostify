package com.capa.boostify.user.service.admin;

import com.capa.boostify.service.AdminService;
import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.exception.InvalidBoosterApplicationDecideDataException;
import com.capa.boostify.repository.BoosterApplicationRepository;
import com.capa.boostify.repository.UserRepository;
import com.capa.boostify.utils.BoosterApplicationDecision;
import com.capa.boostify.utils.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        BoosterApplicationDecision boosterApplicationDecision = new BoosterApplicationDecision();
        boosterApplicationDecision.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecision.setApplicationStatus(ApplicationStatus.DECLINED);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setApplicationStatus(ApplicationStatus.PENDING);

        when(boosterApplicationRepository.findById(boosterApplicationDecision.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));

        String result = adminService.decideBoosterApplication(boosterApplicationDecision);

        verify(boosterApplicationRepository, times(1)).findById(boosterApplicationDecision.getBoosterApplicationId());
        verify(boosterApplicationRepository, times(1)).save(boosterApplication);

        assertEquals("Application " + boosterApplicationDecision.getBoosterApplicationId() + " Status updated for Declined !", result);
        assertEquals(ApplicationStatus.DECLINED, boosterApplication.getApplicationStatus());
    }

    @Test
    public void testDecideBoosterApplicationWithAcceptedStatus() {
        BoosterApplicationDecision boosterApplicationDecision = new BoosterApplicationDecision();
        boosterApplicationDecision.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecision.setApplicationStatus(ApplicationStatus.ACCEPTED);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setApplicationStatus(ApplicationStatus.PENDING);

        when(boosterApplicationRepository.findById(boosterApplicationDecision.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        boosterApplication.setUser(User.builder().id(UUID.randomUUID().toString()).build());
        String result = adminService.decideBoosterApplication(boosterApplicationDecision);

        verify(boosterApplicationRepository, times(1)).findById(boosterApplicationDecision.getBoosterApplicationId());
        verify(boosterApplicationRepository, times(1)).save(boosterApplication);

        assertEquals("Application " + boosterApplicationDecision.getBoosterApplicationId() + " Status updated for Accepted, this user is now booster !", result);
        assertEquals(ApplicationStatus.ACCEPTED, boosterApplication.getApplicationStatus());
    }

    @Test
    public void testDecideBoosterApplicationWithInvalidData() {
        BoosterApplicationDecision boosterApplicationDecision = new BoosterApplicationDecision();

        InvalidBoosterApplicationDecideDataException exception = assertThrows(InvalidBoosterApplicationDecideDataException.class, () -> adminService.decideBoosterApplication(boosterApplicationDecision));

        verify(boosterApplicationRepository, never()).findById(anyString());
        verify(boosterApplicationRepository, never()).save(any(BoosterApplication.class));

        assertEquals("Invalid Data! Cannot decide about booster", exception.getMessage());
    }

    @Test
    void testDecideBoosterApplicationWithPendingStatus() {
        BoosterApplicationDecision boosterApplicationDecision = new BoosterApplicationDecision();
        boosterApplicationDecision.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecision.setApplicationStatus(ApplicationStatus.PENDING);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setApplicationStatus(ApplicationStatus.PENDING);

        when(boosterApplicationRepository.findById(boosterApplicationDecision.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));

        String result = adminService.decideBoosterApplication(boosterApplicationDecision);

        verify(boosterApplicationRepository, times(1)).findById(boosterApplicationDecision.getBoosterApplicationId());
        verify(boosterApplicationRepository, times(1)).save(boosterApplication);

        assertEquals("Application " + boosterApplicationDecision.getBoosterApplicationId() + " Status not changed !", result);
        assertEquals(ApplicationStatus.PENDING, boosterApplication.getApplicationStatus());
    }

    @Test
    void decideBoosterApplication_whenBoosterApplicationIsNotPending_thenShouldReturnAlreadyResolved() {
        BoosterApplicationDecision boosterApplicationDecision = new BoosterApplicationDecision();
        boosterApplicationDecision.setBoosterApplicationId(UUID.randomUUID().toString());
        boosterApplicationDecision.setApplicationStatus(ApplicationStatus.DECLINED);

        BoosterApplication boosterApplication = new BoosterApplication();
        boosterApplication.setId(UUID.randomUUID().toString());
        boosterApplication.setApplicationStatus(ApplicationStatus.ACCEPTED);

        when(boosterApplicationRepository.findById(boosterApplicationDecision.getBoosterApplicationId())).thenReturn(Optional.of(boosterApplication));

        String result = adminService.decideBoosterApplication(boosterApplicationDecision);

        assertEquals(result, "This application is already resolved !");

    }

}