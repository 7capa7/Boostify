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
import com.capa.boostify.user.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BoosterApplicationRepository boosterApplicationRepository;
    private final BoosterDetailsRepository boosterDetailsRepository;
    public String applyForBooster(BoosterApplicationRequest boosterApplicationRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!boosterApplicationRequest.isValid()) throw new InvalidBoosterApplicationDataException();
        if(applicationForThisUserIsAlreadyRegistered(user)) throw new BoosterApplicationAlreadyRegisteredException();
        if(user.getRole().equals(Role.BOOSTER)) return "You are already registered as booster !";
        if(user.getRole().equals(Role.ADMIN)) return "You are already registered as admin !";

        BoosterDetails boosterDetails = boosterDetailsRepository.save(BoosterDetails.builder()
                .inGameHours(boosterApplicationRequest.getInGameHours())
                .firstSeasonYear(boosterApplicationRequest.getFirstSeasonYear())
                .highestDivision(boosterApplicationRequest.getHighestDivision())
                .actualDivision(boosterApplicationRequest.getActualDivision())
                .build());

        BoosterApplication save = boosterApplicationRepository.save(BoosterApplication.builder()
                .boosterApplicationStatus(BoosterApplicationStatus.PENDING)
                .user(user)
                .boosterDetails(boosterDetails)
                .build());

        return "We registered your application, now please wait for decision. Your request id: " +save.getId();
    }
    private boolean applicationForThisUserIsAlreadyRegistered(User user) {
        return boosterApplicationRepository.findBoosterApplicationByUserAndBoosterApplicationStatus(user,BoosterApplicationStatus.PENDING).isPresent();
    }
}
