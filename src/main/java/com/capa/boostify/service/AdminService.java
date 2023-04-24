package com.capa.boostify.service;

import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.exception.InvalidBoosterApplicationDecideDataException;
import com.capa.boostify.repository.BoosterApplicationRepository;
import com.capa.boostify.repository.UserRepository;
import com.capa.boostify.utils.BoosterApplicationDecide;
import com.capa.boostify.utils.BoosterApplicationStatus;
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

    public String decideBoosterApplication(BoosterApplicationDecide boosterApplicationDecide) {
        if (!boosterApplicationDecide.isValid()) throw new InvalidBoosterApplicationDecideDataException();

        BoosterApplication boosterApplication = boosterApplicationRepository.findById(boosterApplicationDecide.getBoosterApplicationId())
                .orElseThrow(InvalidBoosterApplicationDecideDataException::new);

        if (boosterApplication.getBoosterApplicationStatus().equals(BoosterApplicationStatus.PENDING)) {
            boosterApplication.setBoosterApplicationStatus(boosterApplicationDecide.getBoosterApplicationStatus());
            boosterApplicationRepository.save(boosterApplication);

            if (boosterApplicationDecide.getBoosterApplicationStatus().equals(BoosterApplicationStatus.DECLINED)) {
                return "Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status updated for Declined !";
            }

            if (boosterApplicationDecide.getBoosterApplicationStatus().equals(BoosterApplicationStatus.PENDING)) {
                return "Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status not changed !";
            }
            return updateUserToBooster(boosterApplication, boosterApplicationDecide);
        }

        return "This application is already resolved !";
    }

    public List<BoosterApplicationDto> getBoosterApplications(boolean pendingOnly) {
        if (pendingOnly) {
            return mapBoosterApplicationsToDto(boosterApplicationRepository.findBoosterApplicationsByBoosterApplicationStatus(BoosterApplicationStatus.PENDING));
        } else return mapBoosterApplicationsToDto(boosterApplicationRepository.findAll());
    }

    private List<BoosterApplicationDto> mapBoosterApplicationsToDto(List<BoosterApplication> boosterApplications) {
        return boosterApplications.stream().map(boosterApplication -> {
            UserDto userDto =
                    new UserDto(boosterApplication.getUser().getId(), boosterApplication.getUser().getEmail(), boosterApplication.getUser().getRole());

            return new BoosterApplicationDto(boosterApplication.getId(), userDto, boosterApplication.getBoosterDetails(),
                    boosterApplication.getBoosterApplicationStatus());
        }).collect(Collectors.toList());
    }

    private String updateUserToBooster(BoosterApplication boosterApplication, BoosterApplicationDecide boosterApplicationDecide) {
        User user = userRepository.findById(boosterApplication.getUser().getId()).orElseThrow(InvalidBoosterApplicationDecideDataException::new);
        user.setRole(Role.BOOSTER);
        user.setBoosterDetails(boosterApplication.getBoosterDetails());
        userRepository.save(user);

        return "Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status updated for Accepted, this user is now booster !";
    }

}
