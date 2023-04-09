package com.capa.boostify.user.service.admin;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterApplication;
import com.capa.boostify.user.entity.booster.BoosterDetails;
import com.capa.boostify.user.repository.BoosterApplicationRepository;
import com.capa.boostify.user.utils.BoosterApplicationStatus;
import com.capa.boostify.user.utils.Role;
import com.capa.boostify.user.utils.dto.BoosterApplicationDto;
import com.capa.boostify.user.utils.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetBoosterApplicationsTest {
    @Mock
    private BoosterApplicationRepository boosterApplicationRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    void testGetBoosterApplicationsWithPendingOnly() {
        String uuid = UUID.randomUUID().toString();
        List<BoosterApplication> pendingBoosterApplications = new ArrayList<>();
        pendingBoosterApplications.add(new BoosterApplication(uuid, new User(uuid, "user1@example.com", "password", Role.USER, null), new BoosterDetails(), BoosterApplicationStatus.PENDING));
        pendingBoosterApplications.add(new BoosterApplication(uuid, new User(uuid, "user2@example.com", "password", Role.USER, null), new BoosterDetails(), BoosterApplicationStatus.PENDING));

        when(boosterApplicationRepository.findBoosterApplicationsByBoosterApplicationStatus(BoosterApplicationStatus.PENDING))
                .thenReturn(pendingBoosterApplications);

        List<BoosterApplicationDto> result = adminService.getBoosterApplications(true);

        List<BoosterApplicationDto> expected = new ArrayList<>();
        expected.add(new BoosterApplicationDto(uuid, new UserDto(uuid, "user1@example.com", Role.USER), new BoosterDetails(), BoosterApplicationStatus.PENDING));
        expected.add(new BoosterApplicationDto(uuid, new UserDto(uuid, "user2@example.com", Role.USER), new BoosterDetails(), BoosterApplicationStatus.PENDING));

        assertEquals(expected, result);
    }

    @Test
    void testGetBoosterApplicationsWithoutPendingOnly() {
        String uuid = UUID.randomUUID().toString();
        List<BoosterApplication> allBoosterApplications = new ArrayList<>();
        allBoosterApplications.add(new BoosterApplication(uuid, new User(uuid, "user1@example.com", "password", Role.USER, null), new BoosterDetails(), BoosterApplicationStatus.PENDING));
        allBoosterApplications.add(new BoosterApplication(uuid, new User(uuid, "user2@example.com", "password", Role.USER, null), new BoosterDetails(), BoosterApplicationStatus.ACCEPTED));

        when(boosterApplicationRepository.findBoosterApplicationsByBoosterApplicationStatus(BoosterApplicationStatus.PENDING))
                .thenReturn(allBoosterApplications);

        List<BoosterApplicationDto> result = adminService.getBoosterApplications(true);

        List<BoosterApplicationDto> expected = new ArrayList<>();
        expected.add(new BoosterApplicationDto(uuid, new UserDto(uuid, "user1@example.com", Role.USER), new BoosterDetails(), BoosterApplicationStatus.PENDING));
        expected.add(new BoosterApplicationDto(uuid, new UserDto(uuid, "user2@example.com", Role.USER), new BoosterDetails(), BoosterApplicationStatus.ACCEPTED));

        assertEquals(expected, result);
    }
}
