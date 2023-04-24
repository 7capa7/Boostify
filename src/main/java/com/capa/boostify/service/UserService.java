package com.capa.boostify.service;

import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.entity.BoosterDetails;
import com.capa.boostify.exception.BoosterApplicationAlreadyRegisteredException;
import com.capa.boostify.exception.InvalidBoosterApplicationDataException;
import com.capa.boostify.repository.BoosterApplicationRepository;
import com.capa.boostify.repository.BoosterDetailsRepository;
import com.capa.boostify.utils.BoosterApplicationRequest;
import com.capa.boostify.utils.BoosterApplicationStatus;
import com.capa.boostify.utils.Role;
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
