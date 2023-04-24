package com.capa.boostify.service;

import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.entity.BoosterDetails;
import com.capa.boostify.exception.BoosterApplicationAlreadyRegisteredException;
import com.capa.boostify.exception.InvalidBoosterApplicationDataException;
import com.capa.boostify.repository.BoosterApplicationRepository;
import com.capa.boostify.repository.BoosterDetailsRepository;
import com.capa.boostify.utils.BoosterApplicationRequest;
import com.capa.boostify.utils.ApplicationStatus;
import com.capa.boostify.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BoosterApplicationRepository boosterApplicationRepository;
    private final BoosterDetailsRepository boosterDetailsRepository;
    public String applyForBooster(BoosterApplicationRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!request.isValid()) throw new InvalidBoosterApplicationDataException();
        if(applicationForThisUserIsAlreadyRegistered(user)) throw new BoosterApplicationAlreadyRegisteredException();
        if(user.getRole().equals(Role.BOOSTER)) return "You are already registered as booster !";
        if(user.getRole().equals(Role.ADMIN)) return "You are already registered as admin !";

        BoosterDetails details = boosterDetailsRepository.save(BoosterDetails.builder()
                .inGameHours(request.getInGameHours())
                .firstSeasonYear(request.getFirstSeasonYear())
                .highestDivision(request.getHighestDivision())
                .actualDivision(request.getActualDivision())
                .build());

        BoosterApplication save = boosterApplicationRepository.save(BoosterApplication.builder()
                .applicationStatus(ApplicationStatus.PENDING)
                .user(user)
                .boosterDetails(details)
                .build());

        return "We registered your application, now please wait for decision. Your request id: " +save.getId();
    }
    private boolean applicationForThisUserIsAlreadyRegistered(User user) {
        return boosterApplicationRepository.findBoosterApplicationByUserAndApplicationStatus(user, ApplicationStatus.PENDING).isPresent();
    }
}
