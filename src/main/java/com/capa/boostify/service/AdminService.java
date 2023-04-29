package com.capa.boostify.service;

import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.exception.InvalidBoosterApplicationDecideDataException;
import com.capa.boostify.repository.BoosterApplicationRepository;
import com.capa.boostify.repository.UserRepository;
import com.capa.boostify.utils.BoosterApplicationDecision;
import com.capa.boostify.utils.ApplicationStatus;
import com.capa.boostify.utils.Role;
import com.capa.boostify.utils.dto.BoosterApplicationDto;
import com.capa.boostify.utils.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BoosterApplicationRepository boosterApplicationRepository;
    private final UserRepository userRepository;

    public String decideBoosterApplication(String id, ApplicationStatus applicationStatus) {
            BoosterApplicationDecision decision = new BoosterApplicationDecision(id,applicationStatus);
        if (!decision.isValid()) throw new InvalidBoosterApplicationDecideDataException();

        BoosterApplication application = boosterApplicationRepository.findById(decision.getBoosterApplicationId())
                .orElseThrow(InvalidBoosterApplicationDecideDataException::new);

        if (application.getApplicationStatus().equals(ApplicationStatus.PENDING)) {
            application.setApplicationStatus(decision.getApplicationStatus());
            boosterApplicationRepository.save(application);

            if (decision.getApplicationStatus().equals(ApplicationStatus.DECLINED)) {
                return "Application " + decision.getBoosterApplicationId() + " Status updated for Declined !";
            }

            if (decision.getApplicationStatus().equals(ApplicationStatus.PENDING)) {
                return "Application " + decision.getBoosterApplicationId() + " Status not changed !";
            }
            return updateUserToBooster(application, decision);
        }

        return "This application is already resolved !";
    }

    public List<BoosterApplicationDto> getBoosterApplications(boolean pendingOnly) {
        if (pendingOnly) {
            return mapBoosterApplicationsToDto(boosterApplicationRepository.findBoosterApplicationsByApplicationStatus(ApplicationStatus.PENDING));
        } else return mapBoosterApplicationsToDto(boosterApplicationRepository.findAll());
    }

    private List<BoosterApplicationDto> mapBoosterApplicationsToDto(List<BoosterApplication> applications) {
        return applications.stream().map(boosterApplication -> {
            UserDto userDto =
                    new UserDto(boosterApplication.getUser().getId(), boosterApplication.getUser().getEmail(), boosterApplication.getUser().getRole());

            return new BoosterApplicationDto(boosterApplication.getId(), userDto, boosterApplication.getBoosterDetails(),
                    boosterApplication.getApplicationStatus());
        }).collect(Collectors.toList());
    }

    private String updateUserToBooster(BoosterApplication application, BoosterApplicationDecision decision) {
        User user = userRepository.findById(application.getUser().getId()).orElseThrow(InvalidBoosterApplicationDecideDataException::new);
        user.setRole(Role.BOOSTER);
        user.setBoosterDetails(application.getBoosterDetails());
        userRepository.save(user);

        return "Application " + decision.getBoosterApplicationId() + " Status updated for Accepted, this user is now booster !";
    }

}
