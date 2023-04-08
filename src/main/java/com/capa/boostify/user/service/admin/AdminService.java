package com.capa.boostify.user.service.admin;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterApplication;
import com.capa.boostify.user.exception.InvalidBoosterApplicationDecideDataException;
import com.capa.boostify.user.repository.BoosterApplicationRepository;
import com.capa.boostify.user.repository.UserRepository;
import com.capa.boostify.user.utils.BoosterApplicationDecide;
import com.capa.boostify.user.utils.BoosterApplicationStatus;
import com.capa.boostify.user.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

            if(boosterApplicationDecide.getBoosterApplicationStatus().equals(BoosterApplicationStatus.PENDING)){
                return "Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status not changed !";
            }
            return updateUserToBooster(boosterApplication,boosterApplicationDecide);
        }

        return "This application is already resolved !";
    }

    private String updateUserToBooster(BoosterApplication boosterApplication, BoosterApplicationDecide boosterApplicationDecide) {
        User user = userRepository.findById(boosterApplication.getUser().getId()).orElseThrow(InvalidBoosterApplicationDecideDataException::new);
        user.setRole(Role.BOOSTER);
        user.setBoosterDetails(boosterApplication.getBoosterDetails());
        userRepository.save(user);

        return "Application " + boosterApplicationDecide.getBoosterApplicationId() + " Status updated for Accepted, this user is now booster !";
    }
}
