package com.capa.boostify.repository;

import com.capa.boostify.entity.User;
import com.capa.boostify.entity.BoosterApplication;
import com.capa.boostify.utils.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoosterApplicationRepository extends JpaRepository<BoosterApplication, String> {
    Optional<BoosterApplication> findBoosterApplicationByUserAndApplicationStatus(User user, ApplicationStatus applicationStatus);

    List<BoosterApplication> findBoosterApplicationsByApplicationStatus(ApplicationStatus applicationStatus);
}
