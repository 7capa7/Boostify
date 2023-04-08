package com.capa.boostify.utils;

import com.capa.boostify.user.entity.User;
import com.capa.boostify.user.entity.booster.BoosterDetails;
import com.capa.boostify.user.repository.UserRepository;
import com.capa.boostify.user.repository.BoosterDetailsRepository;
import com.capa.boostify.user.utils.Division;
import com.capa.boostify.user.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
public class TestUsersInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BoosterDetailsRepository boosterDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        userRepository.save(User.builder().email("user@user.pl").password(passwordEncoder.encode("password")).role(Role.USER).build());

        userRepository.save(User.builder().email("admin@admin.pl").password(passwordEncoder.encode("password")).role(Role.ADMIN).build());

        BoosterDetails boosterDetails = boosterDetailsRepository.save(BoosterDetails.builder()
                .actualDivision(Division.DIAMOND)
                .highestDivision(Division.CHALLENGER)
                .firstSeasonYear(Year.of(2015))
                .inGameHours(1852)
                .build());
        userRepository.save(User.builder().email("booster@booster.pl").password(passwordEncoder.encode("password")).role(Role.BOOSTER).boosterDetails(boosterDetails).build());

    }
}
