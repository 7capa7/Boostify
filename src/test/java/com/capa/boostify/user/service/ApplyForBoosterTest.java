package com.capa.boostify.user.service;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterApplication;
import com.capa.boostify.user.entity.booster.BoosterDetails;
import com.capa.boostify.user.exception.BoosterApplicationAlreadyRegisteredException;
import com.capa.boostify.user.exception.InvalidBoosterApplicationDataException;
import com.capa.boostify.user.repository.booster.BoosterApplicationRepository;
import com.capa.boostify.user.repository.booster.BoosterDetailsRepository;
import com.capa.boostify.user.utils.BoosterApplicationRequest;
import com.capa.boostify.user.utils.BoosterApplicationStatus;
import com.capa.boostify.user.utils.Division;
import com.capa.boostify.user.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Year;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.UUID;

class ApplyForBoosterTest {
    @Mock
    private BoosterApplicationRepository boosterApplicationRepository;

    @Mock
    private BoosterDetailsRepository boosterDetailsRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void applyForBooster_WithCorrectData_ShouldCreateApplication() {
        BoosterApplicationRequest boosterApplicationRequest = createBoosterApplicationRequest();
        User user = User.builder().role(Role.USER).id(UUID.randomUUID().toString()).build();

        BoosterDetails boosterDetails = BoosterDetails.builder()
                .inGameHours(boosterApplicationRequest.getInGameHours())
                .firstSeasonYear(boosterApplicationRequest.getFirstSeasonYear())
                .highestDivision(boosterApplicationRequest.getHighestDivision())
                .actualDivision(boosterApplicationRequest.getActualDivision())
                .build();

        BoosterApplication boosterApplication = BoosterApplication.builder()
                .boosterApplicationStatus(BoosterApplicationStatus.PENDING)
                .user(user)
                .boosterDetails(boosterDetails)
                .build();

        when(authentication.getPrincipal()).thenReturn(user);
        when(boosterApplicationRepository.findBoosterApplicationByUserAndBoosterApplicationStatus(user, BoosterApplicationStatus.PENDING)).thenReturn(Optional.empty());
        when(boosterDetailsRepository.save(boosterDetails)).thenReturn(boosterDetails);
        when(boosterApplicationRepository.save(boosterApplication)).thenReturn(boosterApplication);

        String result = userService.applyForBooster(boosterApplicationRequest);

        assertEquals("We registered your application, now please wait for decision. Your request id: " + boosterApplication.getId(), result);
    }

    @Test
    void applyForBooster_whenUserIsAdmin_shouldReturnAlreadyRegisteredMessage() {
        BoosterApplicationRequest request = createBoosterApplicationRequest();
        User adminUser = createUser(Role.ADMIN);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(adminUser, null));
        SecurityContextHolder.setContext(securityContext);

        String result = userService.applyForBooster(request);
        assertThat(result).isEqualTo("You are already registered as admin !");
    }

    @Test
    void applyForBooster_whenUserIsBooster_shouldReturnAlreadyRegisteredMessage() {
        BoosterApplicationRequest request = createBoosterApplicationRequest();

        User boosterUser = createUser(Role.BOOSTER);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(boosterUser, null));
        SecurityContextHolder.setContext(securityContext);

        String result = userService.applyForBooster(request);
        assertThat(result).isEqualTo("You are already registered as booster !");
    }


    @Test
    void applyForBoosterWhenApplicationAlreadyRegistered_ShouldThrowError() {
        BoosterApplicationRequest request = createBoosterApplicationRequest();

        User user = createUser(Role.USER);
        BoosterApplication existingApplication = BoosterApplication.builder()
                .id(UUID.randomUUID().toString())
                .user(user)
                .boosterDetails(new BoosterDetails())
                .boosterApplicationStatus(BoosterApplicationStatus.PENDING)
                .build();

        Mockito.when(securityContext.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        Mockito.when(boosterApplicationRepository.findBoosterApplicationByUserAndBoosterApplicationStatus(user, BoosterApplicationStatus.PENDING))
                .thenReturn(Optional.of(existingApplication));

        Throwable exception = assertThrows(BoosterApplicationAlreadyRegisteredException.class, () -> userService.applyForBooster(request));

        assertEquals("We already registered your application! Please wait for decision", exception.getMessage());
        verify(boosterApplicationRepository, Mockito.times(1)).findBoosterApplicationByUserAndBoosterApplicationStatus(user, BoosterApplicationStatus.PENDING);
        verify(boosterDetailsRepository, never()).save(Mockito.any(BoosterDetails.class));
        verify(boosterApplicationRepository, never()).save(Mockito.any(BoosterApplication.class));
    }

    @Test
    void testApplyForBoosterWithInvalidRequestData() {
        User user = createUser(Role.USER);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

        BoosterApplicationRequest boosterApplicationRequest = createBoosterApplicationRequest();
        boosterApplicationRequest.setFirstSeasonYear(Year.of(1222));
        boosterApplicationRequest.setInGameHours(0);

        assertThrows(InvalidBoosterApplicationDataException.class, () -> {
            userService.applyForBooster(boosterApplicationRequest);
        });

        verify(boosterApplicationRepository, never()).save(any(BoosterApplication.class));
    }

    private User createUser(Role role) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email("email")
                .role(role)
                .password("password")
                .build();
    }
    private BoosterApplicationRequest createBoosterApplicationRequest() {
        return BoosterApplicationRequest.builder()
                .inGameHours(1231)
                .firstSeasonYear(Year.of(2016))
                .highestDivision(Division.CHALLENGER)
                .actualDivision(Division.PLATINUM)
                .build();
    }
}